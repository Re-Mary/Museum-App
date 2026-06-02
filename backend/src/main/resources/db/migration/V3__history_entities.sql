-- V3: History & documentation entities
--   condition_report, damage_entry
--   provenance, insurance, loan
--   restoration (Restaurierungsdokumentation — extended)
--   research_report (Forschungsdokumentation)
--   art_historical_report (Kunsthistorische Information)
--   object_document (PDF/file attachments for any report)

-- ---------------------------------------------------------------------------
-- condition_report (Zustandshistorie)
-- ---------------------------------------------------------------------------
CREATE TABLE condition_report (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id               UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    report_date             DATE         NOT NULL,
    created_by_user_id      UUID,
    conservator_person_id   UUID REFERENCES person (id) ON UPDATE CASCADE ON DELETE SET NULL,
    condition_code          VARCHAR(20)  NOT NULL,
    damage_severity         VARCHAR(20),
    summary                 TEXT,
    recommendations         TEXT,
    can_be_exhibited        BOOLEAN,
    can_be_transported      BOOLEAN,
    created_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                 BIGINT       NOT NULL DEFAULT 1,
    is_deleted              BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by              UUID,
    updated_by              UUID,
    client_updated_at       TIMESTAMPTZ,
    last_synced_at          TIMESTAMPTZ,
    sync_status             VARCHAR(20),
    CONSTRAINT chk_condition_report_code
        CHECK (condition_code IN ('good', 'medium', 'critical')),
    CONSTRAINT chk_condition_report_severity
        CHECK (damage_severity IS NULL OR damage_severity IN ('low', 'medium', 'high')),
    CONSTRAINT chk_condition_report_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_condition_report_object_date
    ON condition_report (object_id, report_date DESC)
    WHERE is_deleted = FALSE;

COMMENT ON TABLE condition_report IS 'Condition history per object; current state = latest report (see v_object_current_condition).';

-- ---------------------------------------------------------------------------
-- damage_entry
-- ---------------------------------------------------------------------------
CREATE TABLE damage_entry (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    condition_report_id   UUID         NOT NULL REFERENCES condition_report (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    damage_type_id        UUID         NOT NULL REFERENCES damage_type (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    severity              VARCHAR(20),
    location_text         VARCHAR(255),
    mapping_photo_id      UUID,  -- FK → object_photo added in V5
    notes                 TEXT,
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version               BIGINT       NOT NULL DEFAULT 1,
    is_deleted            BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by            UUID,
    updated_by            UUID,
    client_updated_at     TIMESTAMPTZ,
    last_synced_at        TIMESTAMPTZ,
    sync_status           VARCHAR(20),
    CONSTRAINT chk_damage_entry_severity
        CHECK (severity IS NULL OR severity IN ('low', 'medium', 'high')),
    CONSTRAINT chk_damage_entry_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_damage_entry_condition_report
    ON damage_entry (condition_report_id)
    WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- provenance
-- ---------------------------------------------------------------------------
CREATE TABLE provenance (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id               UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    owner_name              VARCHAR(255),
    owner_person_id         UUID REFERENCES person (id) ON UPDATE CASCADE ON DELETE SET NULL,
    owner_organization_id   UUID REFERENCES organization (id) ON UPDATE CASCADE ON DELETE SET NULL,
    from_date               DATE,
    to_date                 DATE,
    origin                  VARCHAR(500),
    acquisition_type        VARCHAR(50),
    notes                   TEXT,
    created_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                 BIGINT       NOT NULL DEFAULT 1,
    is_deleted              BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by              UUID,
    updated_by              UUID,
    client_updated_at       TIMESTAMPTZ,
    last_synced_at          TIMESTAMPTZ,
    sync_status             VARCHAR(20),
    CONSTRAINT chk_provenance_acquisition_type
        CHECK (acquisition_type IS NULL OR acquisition_type IN (
            'purchase', 'gift', 'loan', 'excavation', 'unknown'
        )),
    CONSTRAINT chk_provenance_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_provenance_object ON provenance (object_id) WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- insurance
-- ---------------------------------------------------------------------------
CREATE TABLE insurance (
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id            UUID          NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    insurer              VARCHAR(255),
    insured_value        DECIMAL(14, 2),
    currency             VARCHAR(3),
    contract_number      VARCHAR(100),
    start_date           DATE,
    end_date             DATE,
    special_conditions   TEXT,
    created_at           TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    version              BIGINT        NOT NULL DEFAULT 1,
    is_deleted           BOOLEAN       NOT NULL DEFAULT FALSE,
    created_by           UUID,
    updated_by           UUID,
    client_updated_at    TIMESTAMPTZ,
    last_synced_at       TIMESTAMPTZ,
    sync_status          VARCHAR(20),
    CONSTRAINT chk_insurance_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_insurance_object ON insurance (object_id) WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- restoration (Restaurierungsdokumentation — extended)
-- ---------------------------------------------------------------------------
CREATE TABLE restoration (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id                   UUID          NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    restaurator_user_id         UUID,
    restaurator_person_id       UUID REFERENCES person (id) ON UPDATE CASCADE ON DELETE SET NULL,
    start_date                  DATE,
    end_date                    DATE,
    status                      VARCHAR(30)   NOT NULL DEFAULT 'draft',
    measures                    TEXT,
    treatment_plan              TEXT,
    materials_used              TEXT,
    reversibility_notes         TEXT,
    ethical_assessment          TEXT,
    report_summary              TEXT,
    cost                        DECIMAL(12, 2),
    currency                    VARCHAR(3),
    condition_report_before_id  UUID REFERENCES condition_report (id) ON UPDATE CASCADE ON DELETE SET NULL,
    condition_report_after_id   UUID REFERENCES condition_report (id) ON UPDATE CASCADE ON DELETE SET NULL,
    notes                       TEXT,
    metadata                    JSONB,
    created_at                  TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    version                     BIGINT        NOT NULL DEFAULT 1,
    is_deleted                  BOOLEAN       NOT NULL DEFAULT FALSE,
    created_by                  UUID,
    updated_by                  UUID,
    client_updated_at           TIMESTAMPTZ,
    last_synced_at              TIMESTAMPTZ,
    sync_status                 VARCHAR(20),
    CONSTRAINT chk_restoration_status
        CHECK (status IN ('draft', 'in_progress', 'completed', 'approved')),
    CONSTRAINT chk_restoration_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_restoration_object ON restoration (object_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_restoration_status ON restoration (status) WHERE is_deleted = FALSE;

COMMENT ON TABLE restoration IS 'Restoration work + Restaurierungsdokumentation; link before/after via condition_report.';

-- ---------------------------------------------------------------------------
-- loan
-- ---------------------------------------------------------------------------
CREATE TABLE loan (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id                   UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    institution                 VARCHAR(255) NOT NULL,
    institution_organization_id UUID REFERENCES organization (id) ON UPDATE CASCADE ON DELETE SET NULL,
    loan_type                   VARCHAR(20)  NOT NULL,
    start_date                  DATE,
    end_date                    DATE,
    insurance_notes             TEXT,
    condition_before            VARCHAR(20),
    condition_after             VARCHAR(20),
    condition_report_before_id  UUID REFERENCES condition_report (id) ON UPDATE CASCADE ON DELETE SET NULL,
    condition_report_after_id   UUID REFERENCES condition_report (id) ON UPDATE CASCADE ON DELETE SET NULL,
    notes                       TEXT,
    created_at                  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                     BIGINT       NOT NULL DEFAULT 1,
    is_deleted                  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by                  UUID,
    updated_by                  UUID,
    client_updated_at           TIMESTAMPTZ,
    last_synced_at              TIMESTAMPTZ,
    sync_status                 VARCHAR(20),
    CONSTRAINT chk_loan_type
        CHECK (loan_type IN ('incoming', 'outgoing')),
    CONSTRAINT chk_loan_condition_before
        CHECK (condition_before IS NULL OR condition_before IN ('good', 'medium', 'critical')),
    CONSTRAINT chk_loan_condition_after
        CHECK (condition_after IS NULL OR condition_after IN ('good', 'medium', 'critical')),
    CONSTRAINT chk_loan_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_loan_object ON loan (object_id) WHERE is_deleted = FALSE;

-- ---------------------------------------------------------------------------
-- research_report (Forschungsdokumentation)
-- ---------------------------------------------------------------------------
CREATE TABLE research_report (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id             UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    report_date           DATE         NOT NULL,
    research_type         VARCHAR(50)  NOT NULL,
    title                 VARCHAR(255),
    institution           VARCHAR(255),
    lab_name              VARCHAR(255),
    researcher_person_id  UUID REFERENCES person (id) ON UPDATE CASCADE ON DELETE SET NULL,
    researcher_user_id    UUID,
    methodology           TEXT,
    sample_description    TEXT,
    results               TEXT,
    conclusions           TEXT,
    status                VARCHAR(20)  NOT NULL DEFAULT 'draft',
    metadata              JSONB,
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version               BIGINT       NOT NULL DEFAULT 1,
    is_deleted            BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by            UUID,
    updated_by            UUID,
    client_updated_at     TIMESTAMPTZ,
    last_synced_at        TIMESTAMPTZ,
    sync_status           VARCHAR(20),
    CONSTRAINT chk_research_report_type
        CHECK (research_type IN (
            'xrf', 'ir', 'uv', 'xray', 'dendrochronology',
            'radiocarbon', 'petrography', 'microscopy', 'other'
        )),
    CONSTRAINT chk_research_report_status
        CHECK (status IN ('draft', 'in_review', 'final')),
    CONSTRAINT chk_research_report_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_research_report_object ON research_report (object_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_research_report_type ON research_report (research_type) WHERE is_deleted = FALSE;
CREATE INDEX idx_research_report_metadata ON research_report USING GIN (metadata) WHERE is_deleted = FALSE;

COMMENT ON TABLE research_report IS 'Scientific / technical research documentation (Forschungsdokumentation).';

-- ---------------------------------------------------------------------------
-- art_historical_report (Kunsthistorische Information)
-- ---------------------------------------------------------------------------
CREATE TABLE art_historical_report (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id                   UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    report_date                 DATE         NOT NULL,
    title                       VARCHAR(255),
    author_person_id            UUID REFERENCES person (id) ON UPDATE CASCADE ON DELETE SET NULL,
    author_user_id              UUID,
    attribution                 VARCHAR(255),
    attribution_certainty       VARCHAR(20),
    dating_text                 VARCHAR(255),
    dating_from                 DATE,
    dating_to                   DATE,
    style_period                VARCHAR(255),
    subject_iconography         TEXT,
    technique_analysis          TEXT,
    historical_context          TEXT,
    bibliography                TEXT,
    exhibition_history_summary  TEXT,
    status                      VARCHAR(20)  NOT NULL DEFAULT 'draft',
    version_number              INTEGER      NOT NULL DEFAULT 1,
    supersedes_report_id        UUID REFERENCES art_historical_report (id) ON UPDATE CASCADE ON DELETE SET NULL,
    metadata                    JSONB,
    created_at                  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                     BIGINT       NOT NULL DEFAULT 1,
    is_deleted                  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by                  UUID,
    updated_by                  UUID,
    client_updated_at           TIMESTAMPTZ,
    last_synced_at              TIMESTAMPTZ,
    sync_status                 VARCHAR(20),
    CONSTRAINT chk_art_historical_certainty
        CHECK (attribution_certainty IS NULL OR attribution_certainty IN (
            'certain', 'probable', 'doubtful', 'unknown'
        )),
    CONSTRAINT chk_art_historical_status
        CHECK (status IN ('draft', 'in_review', 'approved', 'superseded')),
    CONSTRAINT chk_art_historical_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_art_historical_report_object
    ON art_historical_report (object_id, report_date DESC)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_art_historical_report_fts
    ON art_historical_report USING GIN (
        to_tsvector('simple',
            coalesce(title, '') || ' ' ||
            coalesce(attribution, '') || ' ' ||
            coalesce(subject_iconography, '') || ' ' ||
            coalesce(historical_context, '')
        )
    ) WHERE is_deleted = FALSE;

COMMENT ON TABLE art_historical_report IS 'Art-historical information sheet (Kunsthistorische Information); versioned via supersedes_report_id.';

-- ---------------------------------------------------------------------------
-- object_document (PDF / file attachments for reports)
-- ---------------------------------------------------------------------------
CREATE TABLE object_document (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    object_id             UUID         NOT NULL REFERENCES museum_object (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    linked_entity_type    VARCHAR(50)  NOT NULL,
    linked_entity_id      UUID         NOT NULL,
    document_kind         VARCHAR(50)  NOT NULL,
    title                 VARCHAR(255),
    file_uri              TEXT         NOT NULL,
    mime_type             VARCHAR(100),
    file_size_bytes       BIGINT,
    checksum_sha256       VARCHAR(64),
    uploaded_at           TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version               BIGINT       NOT NULL DEFAULT 1,
    is_deleted            BOOLEAN      NOT NULL DEFAULT FALSE,
    created_by            UUID,
    updated_by            UUID,
    client_updated_at     TIMESTAMPTZ,
    last_synced_at        TIMESTAMPTZ,
    sync_status           VARCHAR(20),
    CONSTRAINT chk_object_document_entity_type
        CHECK (linked_entity_type IN (
            'condition_report', 'restoration', 'research_report',
            'art_historical_report', 'loan', 'provenance'
        )),
    CONSTRAINT chk_object_document_kind
        CHECK (document_kind IN (
            'report_pdf', 'expert_opinion', 'lab_result',
            'treatment_plan', 'scan', 'other'
        )),
    CONSTRAINT chk_object_document_sync_status
        CHECK (sync_status IS NULL OR sync_status IN ('pending', 'synced', 'conflict'))
);

CREATE INDEX idx_object_document_object
    ON object_document (object_id)
    WHERE is_deleted = FALSE;

CREATE INDEX idx_object_document_linked
    ON object_document (linked_entity_type, linked_entity_id)
    WHERE is_deleted = FALSE;

COMMENT ON TABLE object_document IS 'File attachments (MinIO URIs) linked polymorphically to reports and documentation.';

-- ---------------------------------------------------------------------------
-- Role: RESEARCHER
-- ---------------------------------------------------------------------------
INSERT INTO app_role (name, description) VALUES
    ('RESEARCHER', 'Research reports, scientific analysis documentation')
ON CONFLICT (name) DO NOTHING;

-- ---------------------------------------------------------------------------
-- View: current condition per object
-- ---------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_object_current_condition AS
SELECT DISTINCT ON (object_id)
    object_id,
    id              AS condition_report_id,
    condition_code,
    report_date,
    can_be_exhibited,
    can_be_transported
FROM condition_report
WHERE is_deleted = FALSE
ORDER BY object_id, report_date DESC, created_at DESC;
