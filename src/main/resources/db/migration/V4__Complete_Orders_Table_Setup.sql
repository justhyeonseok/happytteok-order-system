-- V4: Complete Orders Table Setup with isAllDay support
-- 단순하고 확실한 마이그레이션

-- 1. memo 컬럼 추가 (있는 경우 무시됨)
ALTER TABLE orders ADD COLUMN IF NOT EXISTS memo VARCHAR(500);

-- 2. is_all_day 컬럼 추가 (있는 경우 무시됨)
ALTER TABLE orders ADD COLUMN IF NOT EXISTS is_all_day BOOLEAN DEFAULT false;

-- 3. 기존 데이터 정리
UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;

-- 4. NOT NULL 제약조건 추가
ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT false;

-- 5. 기존 주문 데이터 정리
DELETE FROM order_table;
DELETE FROM orders;
