package com.gk.happytteokordersystem.domain.customer.repository

import com.gk.happytteokordersystem.domain.customer.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    // 이름 또는 전화번호로 검색 시 페이징 처리
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:search% OR c.phone LIKE %:search%")
    fun findByNameOrPhoneContaining(@Param("search") search: String, pageable: Pageable): Page<Customer>

    // 주문량 순으로 정렬하기 위한 쿼리
    @Query("SELECT c, COUNT(o) as orderCount FROM Customer c JOIN c.orders o GROUP BY c ORDER BY orderCount DESC")
    fun findAllByOrderByOrderCountDesc(pageable: Pageable): Page<Customer>

    // 모든 고객을 페이징하여 조회
    override fun findAll(pageable: Pageable): Page<Customer>
}