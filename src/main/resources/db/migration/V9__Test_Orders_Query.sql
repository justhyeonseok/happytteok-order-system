-- Orders 테이블 구조 확인 및 최소 수정
-- 현재 테이블 구조를 확인하고 필요한 경우만 수정

-- Orders 테이블에 필요한 컬럼이 있는지 확인하고 없으면 추가
DO $$
BEGIN
    -- memo 컬럼 추가 (이미 있다면 무시됨)
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'memo'
    ) THEN
        ALTER TABLE orders ADD COLUMN memo VARCHAR(500);
    END IF;
    
    -- is_all_day 컬럼이 false 기본값인지 확인하고 수정
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'is_all_day' AND is_nullable = 'YES'
    ) THEN
        -- null 값을 false로 변경
        UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;
        -- NOT NULL 제약조건 추가
        ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
    END IF;
END $$;
