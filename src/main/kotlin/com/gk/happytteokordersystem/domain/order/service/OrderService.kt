package com.gk.happytteokordersystem.domain.order.service

import com.gk.happytteokordersystem.domain.order.dto.OrderCreateReq
import com.gk.happytteokordersystem.domain.order.dto.OrderListRes
import com.gk.happytteokordersystem.domain.order.dto.OrderRes
import com.gk.happytteokordersystem.domain.order.dto.OrderUpdateReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderService {
    fun createOrder(requestDto: OrderCreateReq): OrderRes
    fun getOrderDetails(orderId: Long): OrderRes
    fun updateOrder(orderId: Long, requestDto: OrderUpdateReq): OrderRes
    fun getOrdersByCustomer(customerId: Long, pageable: Pageable): Page<OrderRes>
    fun getAllOrders(pageable: Pageable): Page<OrderListRes>
    fun deleteOrders(ids: List<Long>)
    fun paidStatus(id: Long)
    fun pickUpStatus(id: Long)
    fun riceStatus(id: Long)
}