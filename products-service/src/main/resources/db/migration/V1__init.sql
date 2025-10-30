-- ================================================
-- Flyway Migration Script: V1__init.sql
-- Descripción: Crea la tabla products para el microservicio Products Service
-- ================================================

CREATE TABLE IF NOT EXISTS products (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  code VARCHAR(64) NOT NULL UNIQUE,
  description TEXT,
  price NUMERIC(15,2) NOT NULL CHECK (price >= 0),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE,
  version INTEGER DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_products_code ON products(code);