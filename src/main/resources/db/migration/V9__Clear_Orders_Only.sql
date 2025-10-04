-- V9: 주문 데이터만 초기화 (customers, product_type은 유지)

-- 1. order_table 데이터 삭제 (외래키 참조로 먼저 삭제)
DELETE FROM order_table;

-- 2. orders 데이터 삭제
DELETE FROM orders;

-- 3. 시퀀스 초기화 (새 주문이 1번부터 시작하도록)
ALTER SEQUENCE orders_id_seq RESTART WITH 1;
ALTER SEQUENCE order_table_id_seq RESTART WITH 1;

-- 완료: customers와 product_type 테이블은 그대로 유지됨
