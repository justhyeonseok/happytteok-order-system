-- V6: Force update all is_all_day values to fix deployment issues
-- This migration ensures all existing data has proper boolean values

-- First, make the column nullable temporarily to handle existing data
ALTER TABLE orders ALTER COLUMN is_all_day DROP NOT NULL;

-- Update all NULL values to FALSE
UPDATE orders SET is_all_day = FALSE WHERE is_all_day IS NULL;

-- Then make it NOT NULL again with default FALSE
ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT FALSE;

-- Add comment for documentation
COMMENT ON COLUMN orders.is_all_day IS 'Forced update: All orders now have boolean is_all_day values';
