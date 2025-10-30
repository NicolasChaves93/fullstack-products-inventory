-- Flyway V1: create inventories table
CREATE TABLE IF NOT EXISTS inventories (
  id BIGSERIAL PRIMARY KEY,
  product_code VARCHAR(64) NOT NULL,
  quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
  last_updated TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  version INTEGER DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_inventories_product_code ON inventories(product_code);