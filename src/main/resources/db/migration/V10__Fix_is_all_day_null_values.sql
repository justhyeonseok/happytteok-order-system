-- V10: is_all_day null 값 수정
UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;
ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
