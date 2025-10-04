-- V10: 주문 데이터만 초기화 (고객, 제품 데이터는 보존)
-- 서비스 주문 데이터를 삭제하고 깨끗한 상태로 재시작
DELETE FROM order_table;
DELETE FROM orders;
