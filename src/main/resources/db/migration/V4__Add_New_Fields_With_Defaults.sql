-- V4: Add new fields with safe defaults for existing data
-- This migration ensures backward compatibility

-- Add is_all_day column with default value false for existing orders
ALTER TABLE orders ADD COLUMN IF NOT EXISTS is_all_day BOOLEAN DEFAULT FALSE NOT NULL;

-- Add memo column (nullable, so no default needed)
ALTER TABLE orders ADD COLUMN IF NOT EXISTS memo VARCHAR(255);

-- Add has_rice column to order_table (nullable, so no default needed)
ALTER TABLE order_table ADD COLUMN IF NOT EXISTS has_rice BOOLEAN;

-- Update existing orders to have is_all_day = false (explicit update for safety)
UPDATE orders SET is_all_day = FALSE WHERE is_all_day IS NULL;

-- Add comments for documentation
COMMENT ON COLUMN orders.is_all_day IS 'Indicates if pickup is available all day (00:00 time)';
COMMENT ON COLUMN orders.memo IS 'Optional memo for the order';
COMMENT ON COLUMN order_table.has_rice IS 'Indicates if this specific item includes rice accompaniment';
