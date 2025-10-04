-- V8: 기존 null 값을 가진 is_all_day 컬럼 수정

-- 1. 임시로 NOT NULL 제약조건 제거 (컬럼이 이미 exists 한다면)
DO $$ 
BEGIN 
    -- is_all_day 컬럼이 있고 NOT NULL 제약조건이 있으면 제거
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' 
        AND column_name = 'is_all_day' 
        AND is_nullable = 'NO'
    ) THEN 
        ALTER TABLE orders ALTER COLUMN is_all_day DROP NOT NULL;
    END IF;
END $$;

-- 2. 기존 null 값들을 false로 업데이트
UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;

-- 3. 다시 NOT NULL 제약조건 추가
ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;

-- 4. 기본값 설정
ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT false;
