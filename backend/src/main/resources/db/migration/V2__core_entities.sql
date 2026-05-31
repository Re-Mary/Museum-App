-- V2: Core entities — person, organization, storage, container, museum_object
--
-- Identifikationsstrategie (museum_object):
--   id (UUID)           = eindeutige System-ID (PK, technische Wahrheit)
--   system_number       = optional eindeutige Museumsnummer (partial UNIQUE)
--   inventar_number     = optional, NICHT eindeutig, NULL erlaubt
--   part_number         = optional, unterscheidet Serienteile
--   parent_object_id    = optional, Hierarchie Serie → Teile

-- ---------------------------------------------------------------------------
-- person
-- ---------------------------------------------------------------------------
CREATE TABLE person (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name     VARCHAR(255) NOT NULL,
    birth_year    INTEGER,
    death_year    INTEGER,
    biography     TEXT,
    external_uri  VARCHAR(500),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version       BIGINT       NOT NULL DEFAULT 1,
    is_deleted    BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by    UUID,
    updated_by    UUID,
    client_updated_at TIMESTAMPTZ,
    last_synced_at    TIMESTAMPTZ,
    sync_status   VARCHAR(20),
    CONSTRAINT chk_person_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

-- ---------------------------------------------------------------------------
-- organization
-- ---------------------------------------------------------------------------
CREATE TABLE organization (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(255) NOT NULL,
    city          VARCHAR(120),
    country       VARCHAR(120),
    external_uri  VARCHAR(500),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version       BIGINT       NOT NULL DEFAULT 1,
    is_deleted    BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by    UUID,
    updated_by    UUID,
    client_updated_at TIMESTAMPTZ,
    last_synced_at    TIMESTAMPTZ,
    sync_status   VARCHAR(20),
    CONSTRAINT chk_organization_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

-- ---------------------------------------------------------------------------
-- storage_location
-- ---------------------------------------------------------------------------
CREATE TABLE storage_location (
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code               VARCHAR(100) NOT NULL,
    name               VARCHAR(255),
    shelf_system_type  VARCHAR(50),
    description        TEXT,
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version            BIGINT       NOT NULL DEFAULT 1,
    is_deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by         UUID,
    updated_by         UUID,
    client_updated_at  TIMESTAMPTZ,
    last_synced_at     TIMESTAMPTZ,
    sync_status        VARCHAR(20),
    CONSTRAINT uq_storage_location_code UNIQUE (code),
    CONSTRAINT chk_storage_location_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

-- ---------------------------------------------------------------------------
-- container (before museum_object — FK from object)
-- ---------------------------------------------------------------------------
CREATE TABLE container (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    container_number    VARCHAR(100) NOT NULL,
    container_type      VARCHAR(50),
    storage_location_id UUID REFERENCES storage_location (id) ON UPDATE CASCADE ON DELETE SET NULL,
    notes               TEXT,
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version             BIGINT       NOT NULL DEFAULT 1,
    is_deleted          BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by          UUID,
    updated_by          UUID,
    client_updated_at   TIMESTAMPTZ,
    last_synced_at      TIMESTAMPTZ,
    sync_status         VARCHAR(20),
    CONSTRAINT uq_container_number UNIQUE (container_number),
    CONSTRAINT chk_container_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_container_storage_location ON container (storage_location_id)
    WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- museum_object
-- ---------------------------------------------------------------------------
CREATE TABLE museum_object (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Identifikation (siehe ERD v2 §5.1)
    system_number           VARCHAR(50),   -- optional, eindeutig wenn gesetzt
    inventar_number         VARCHAR(100),  -- optional, Duplikate erlaubt
    part_number             VARCHAR(50),   -- optional, z.B. .1, .2, Teil A
    parent_object_id        UUID,          -- Serie/Sammelobjekt → Teile

    -- Kernattribute
    title                   VARCHAR(255) NOT NULL,
    object_date_text        VARCHAR(100),
    object_date_from        DATE,
    object_date_to          DATE,
    technique               VARCHAR(255),
    height_cm               DECIMAL(10, 2),
    width_cm                DECIMAL(10, 2),
    depth_cm                DECIMAL(10, 2),

    -- Beziehungen
    owner_organization_id   UUID REFERENCES organization (id) ON UPDATE CASCADE ON DELETE SET NULL,
    storage_location_id     UUID REFERENCES storage_location (id) ON UPDATE CASCADE ON DELETE SET NULL,
    container_id            UUID REFERENCES container (id) ON UPDATE CASCADE ON DELETE SET NULL,

    can_be_exhibited        BOOLEAN,
    can_be_transported      BOOLEAN,
    notes                   TEXT,
    metadata                JSONB,

    -- Sync-Felder
    created_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                 BIGINT       NOT NULL DEFAULT 1,
    is_deleted              BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by              UUID,
    updated_by              UUID,
    client_updated_at       TIMESTAMPTZ,
    last_synced_at          TIMESTAMPTZ,
    sync_status             VARCHAR(20),

    CONSTRAINT fk_museum_object_parent
        FOREIGN KEY (parent_object_id) REFERENCES museum_object (id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_museum_object_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

COMMENT ON TABLE museum_object IS
    'Museum inventory object. Technical identity = id (UUID). inventar_number is catalog metadata, not unique.';
COMMENT ON COLUMN museum_object.id IS 'Unique system ID — all FKs and API paths use this UUID';
COMMENT ON COLUMN museum_object.system_number IS 'Optional human-readable museum number (unique when assigned)';
COMMENT ON COLUMN museum_object.inventar_number IS 'Optional catalog number; may be NULL or duplicated across objects';
COMMENT ON COLUMN museum_object.part_number IS 'Optional part suffix for series (e.g. .1, .2)';
COMMENT ON COLUMN museum_object.parent_object_id IS 'Optional parent for series/collection grouping';

-- ---------------------------------------------------------------------------
-- Indizes: Identifikationsstrategie (ERD v2 §7)
-- ---------------------------------------------------------------------------

-- Eindeutige Museumsnummer (nur wenn vergeben)
CREATE UNIQUE INDEX uq_museum_object_system_number
    ON museum_object (system_number)
    WHERE system_number IS NOT NULL AND is_deleted = FALSE;

-- Inventarnummer: Suche, KEIN UNIQUE — Duplikate erlaubt
CREATE INDEX idx_museum_object_inventar_number
    ON museum_object (inventar_number)
    WHERE is_deleted = FALSE;

-- Serienteile gruppieren
CREATE INDEX idx_museum_object_inventar_part
    ON museum_object (inventar_number, part_number)
    WHERE is_deleted = FALSE;

-- Hierarchie Serie → Teile
CREATE INDEX idx_museum_object_parent
    ON museum_object (parent_object_id)
    WHERE is_deleted = FALSE AND parent_object_id IS NOT NULL;

CREATE INDEX idx_museum_object_storage_location
    ON museum_object (storage_location_id)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_museum_object_container
    ON museum_object (container_id)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_museum_object_updated_at
    ON museum_object (updated_at)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_museum_object_metadata
    ON museum_object USING GIN (metadata)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_museum_object_title_fts
    ON museum_object USING GIN (to_tsvector('simple', coalesce(title, '') || ' ' || coalesce(notes, '')))
    WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- Sequence für system_number (optional auto-assign: MUS-YYYY-NNNNNN)
-- ---------------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS museum_object_system_number_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE FUNCTION generate_system_number()
RETURNS VARCHAR(50)
LANGUAGE plpgsql
AS $$
DECLARE
    year_part VARCHAR(4) := to_char(NOW(), 'YYYY');
    seq_part  VARCHAR(6) := lpad(nextval('museum_object_system_number_seq')::TEXT, 6, '0');
BEGIN
    RETURN 'MUS-' || year_part || '-' || seq_part;
END;
$$;

COMMENT ON FUNCTION generate_system_number IS
    'Generates next museum system_number, e.g. MUS-2026-000001. Assign in application on create if not provided.';
