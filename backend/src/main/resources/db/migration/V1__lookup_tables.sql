-- V1: Lookup / reference tables (simplified schema, no full sync fields)

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ---------------------------------------------------------------------------
-- app_role (PostgreSQL reserves keyword "role")
-- ---------------------------------------------------------------------------
CREATE TABLE app_role (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(50)  NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_app_role_name UNIQUE (name)
);

INSERT INTO app_role (name, description) VALUES
    ('ADMIN',           'Full access, user management'),
    ('CURATOR',         'Objects, exhibitions, provenance, media'),
    ('CONSERVATOR',     'Condition reports, restoration'),
    ('STORAGE_MANAGER', 'Storage, containers, climate data'),
    ('GUEST',           'Read-only access');

-- ---------------------------------------------------------------------------
-- category
-- ---------------------------------------------------------------------------
CREATE TABLE category (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_category_name UNIQUE (name)
);

INSERT INTO category (name) VALUES
    ('painting'), ('sculpture'), ('object'), ('textile'),
    ('graphic'), ('mix-media'), ('digital media');

-- ---------------------------------------------------------------------------
-- material + submaterial
-- ---------------------------------------------------------------------------
CREATE TABLE material (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_material_name UNIQUE (name)
);

CREATE TABLE submaterial (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    material_id UUID         NOT NULL REFERENCES material (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    name        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_submaterial_material_name UNIQUE (material_id, name)
);

INSERT INTO material (name) VALUES
    ('Wood'), ('Textile'), ('Stone'), ('Ceramic'), ('Glass'),
    ('Paper'), ('Metal'), ('Paint Layer'), ('Leather'), ('Plastic'), ('Graphic'), ('Synthetic Material');

-- Wood
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Pine'), ('Pappel'), ('Cedar')) AS s(name)
WHERE m.name = 'Wood';

-- Textile
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Linen'), ('Cotton'), ('Wool'), ('Silk'), ('Synthetic')) AS s(name)
WHERE m.name = 'Textile';

-- Stone
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Limestone'), ('Sandstone'), ('Granite'), ('Marble'), ('Porphyria')) AS s(name)
WHERE m.name = 'Stone';

-- Ceramic
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Terracotta')) AS s(name)
WHERE m.name = 'Ceramic';

-- Glass
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Lead glass')) AS s(name)
WHERE m.name = 'Glass';

-- Paper
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Handmade')) AS s(name)
WHERE m.name = 'Paper';

-- Metal
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Cooper'), ('Iron'), ('Alloy')) AS s(name)
WHERE m.name = 'Metal';

-- Paint Layer
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Oil'), ('Glue'), ('Watercolour'), ('Acryl'), ('Mixed Media')) AS s(name)
WHERE m.name = 'Paint Layer';

-- Leather
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Leather'), ('Suede'), ('Nubuck'), ('Patent')) AS s(name)
WHERE m.name = 'Leather';

-- Plastic
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES ('Unknown'), ('Polyethylene'), ('Polypropylene'), ('Polystyrene'), ('Polyvinylchloride'), ('Polyurethane')) AS s(name)
WHERE m.name = 'Plastic';

-- Graphic
INSERT INTO submaterial (material_id, name)
SELECT m.id, s.name FROM material m
CROSS JOIN (VALUES
    ('Unknown'), ('Graphite Pencil'), ('Colour Pencil'),
    ('Charcoal'), ('Sanguine'), ('Ink')
) AS s(name)
WHERE m.name = 'Graphic';

-- ---------------------------------------------------------------------------
-- damage_type
-- ---------------------------------------------------------------------------
CREATE TABLE damage_type (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    is_system   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_damage_type_name UNIQUE (name)
);

INSERT INTO damage_type (name) VALUES
    ('crack'), ('split'), ('bruised edges and corners'), ('losses'),
    ('deformation'), ('colour changes'), ('dirty surface');

-- ---------------------------------------------------------------------------
-- packaging_type
-- ---------------------------------------------------------------------------
CREATE TABLE packaging_type (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_packaging_type_name UNIQUE (name)
);

INSERT INTO packaging_type (name) VALUES
    ('Wood box'), ('Special container'), ('Ethafoam'),
    ('bubble wrap/pack'), ('acid free paper'), ('acid free carton');

-- ---------------------------------------------------------------------------
-- transport_type
-- ---------------------------------------------------------------------------
CREATE TABLE transport_type (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_transport_type_name UNIQUE (name)
);

INSERT INTO transport_type (name) VALUES
    ('Shipping'), ('Flight'), ('LKW');
