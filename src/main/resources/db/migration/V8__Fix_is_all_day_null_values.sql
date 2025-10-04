-- V8: 기존 null 값을 가진 is_all_day 컬럼 수정 (안전 버전)

-- 1. is_all_day 컬럼이 존재하는지 확인
DO $$ 
DECLARE 
    col_exists BOOLEAN;
BEGIN 
    SELECT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' 
        AND column_name = 'is_all_day'
    ) INTO col_exists;
    
    -- 컬럼이 존재하지 않으면 생성
    IF NOT col_exists THEN
        ALTER TABLE orders ADD COLUMN is_all_day BOOLEAN DEFAULT false;
    ELSE
        -- 컬럼이 존재하면 null 값들을 false로 업데이트
        UPDATE orders SET is_all_day = false WHERE is_all_day IS NULL;
        
        -- 기본값 설정
        ALTER TABLE orders ALTER COLUMN is_all_day SET DEFAULT false;
        
        -- NOT NULL 제약조건 추가 (이미 있는지 확인)
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.table_constraints tc
            JOIN information_schema.constraint_column_usage ccu ON tc.constraint_name = ccu.constraint_name
            WHERE tc.table_name = 'orders' 
            AND ccu.column_name = 'is_all_day'
            AND tc.constraint_type = 'CHECK'
        ) THEN
            ALTER TABLE orders ALTER COLUMN is_all_day SET NOT NULL;
        END IF;
    END IF;
END $$;
