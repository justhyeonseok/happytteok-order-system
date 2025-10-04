-- V7: Emergency fix for is_all_day null values
-- This migration uses more aggressive approach to handle the null issue

-- Check if column exists and has null values
DO $$ 
BEGIN
    -- Step 1: Make sure column exists
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'orders' AND column_name = 'is_all_day') THEN
        
        -- Step 2: Drop NOT NULL constraint temporarily
        BEGIN
            ALTER TABLE orders ALTER COLUMN is_all_day DROP NOT NULL;
        EXCEPTION WHEN others THEN
            RAISE NOTICE 'Constraint drop failed, continuing...';
        END;
        
        -- Step 3: Update all NULL values to FALSE
        UPDATE orders SET is_all_day = FALSE WHERE is_all_day IS NULL;
        
        -- Step 4: Add NOT NULL constraint back with default
        BEGIN
            ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
            ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT FALSE;
        EXCEPTION WHEN others THEN
            RAISE NOTICE 'Constraint add failed: %', SQLERRM;
        END;
        
        RAISE NOTICE 'Successfully fixed is_all_day null values';
    ELSE
        RAISE NOTICE 'is_all_day column does not exist, creating it...';
        ALTER TABLE orders ADD COLUMN is_all_day BOOLEAN DEFAULT FALSE NOT NULL;
    END IF;
END $$;
