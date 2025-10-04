-- V5: Fix null values in is_all_day column
-- This migration fixes existing null values in the database

-- Update all NULL values in is_all_day column to FALSE
UPDATE orders SET is_all_day = FALSE WHERE is_all_day IS NULL;

-- Add comment for documentation
COMMENT ON COLUMN orders.is_all_day IS 'Fixed null values: All existing orders set to FALSE (not all-day pickup)';
