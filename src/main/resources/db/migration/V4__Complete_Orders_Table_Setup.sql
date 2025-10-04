-- V4: Complete Orders Table Setup with isAllDay support
-- 주문 테이블 완전 설정 (isAllDay 포함)

-- 1. orders 테이블에 필요한 컬럼들 추가 (없는 경우에만)
DO $$
BEGIN
    -- memo 컬럼 추가
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'memo'
    ) THEN
        ALTER TABLE orders ADD COLUMN memo VARCHAR(500);
    END IF;
    
    -- is_all_day 컬럼 추가
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'is_all_day'
    ) THEN
        ALTER TABLE orders ADD COLUMN is_all_day BOOLEAN DEFAULT false;
    END IF;
END $$;

-- 2. 기존 데이터 정리 (null 값들을 false로 변경)
UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;

-- 3. NOT NULL 제약조건 추가 (안전하게)
DO $$
BEGIN
    -- is_all_day 컬럼을 NOT NULL로 변경
    ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
    ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT false;
EXCEPTION
    WHEN OTHERS THEN
        -- 제약조건 추가 실패 시 무시 (이미 NOT NULL인 경우)
        NULL;
END $$;

-- 4. 기존 주문 데이터 정리 (필요시)
DELETE FROM order_table;
DELETE FROM orders;
