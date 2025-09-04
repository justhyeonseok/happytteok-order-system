package com.gk.happytteokordersystem.domain.order.repository

import com.gk.happytteokordersystem.domain.order.model.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByCustomerId(customerId: Long, pageable: Pageable): Page<Order>
    fun findAllByIdIn(ids: List<Long>): List<Order>
}