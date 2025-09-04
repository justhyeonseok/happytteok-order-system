package com.gk.happytteokordersystem.domain.order.service

import com.gk.happytteokordersystem.domain.customer.repository.CustomerRepository
import com.gk.happytteokordersystem.domain.order.dto.OrderCreateReq
import com.gk.happytteokordersystem.domain.order.dto.OrderListRes
import com.gk.happytteokordersystem.domain.order.dto.OrderRes
import com.gk.happytteokordersystem.domain.order.model.Order
import com.gk.happytteokordersystem.domain.order.model.OrderTable
import com.gk.happytteokordersystem.domain.order.repository.OrderRepository
import com.gk.happytteokordersystem.domain.order.repository.ProductTypeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Service
@Transactional
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productTypeRepository: ProductTypeRepository
) : OrderService {

    @Transactional
    override fun createOrder(requestDto: OrderCreateReq): OrderRes {
        val customer = customerRepository.findByIdOrNull(requestDto.customerId)
            ?: throw NoSuchElementException("Customer with id ${requestDto.customerId} not found")

        val orderTables = requestDto.orderTables.map {
            val productType = productTypeRepository.findByIdOrNull(it.productId)
                ?: throw NoSuchElementException("product with id ${it.productId} not found")
            OrderTable(
                id = 0,
                quantity = it.quantity,
                productType = productType
            )
        }.toMutableList()

        val totalPrice = orderTables.sumOf { it.productType.price.multiply(BigDecimal(it.quantity)) }
        val newOrder = Order(
            id = 0,
            orderUid = generateOrderUid(),
            customer = customer,
            orderTable = orderTables,
            totalPrice = totalPrice,
            pickupDate = requestDto.pickupDate,
            isPaid = requestDto.isPaid,
            hasRice = requestDto.hasRice,
            isPickedUp = requestDto.isPickedUp,
            orderDate = OffsetDateTime.now()
        )

        // orderTable에 order를 연결
        orderTables.forEach { it.order = newOrder }

        val savedOrder = orderRepository.save(newOrder)
        return OrderRes.from(savedOrder)
    }

    @Transactional(readOnly = true)
    override fun getOrderDetails(orderId: Long): OrderRes {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw NoSuchElementException("Order with id $orderId not found")
        return OrderRes.from(order)
    }

    @Transactional
    override fun updateOrder(orderId: Long, requestDto: OrderCreateReq): OrderRes {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw NoSuchElementException("Order with id $orderId not found")
        val customer = customerRepository.findByIdOrNull(requestDto.customerId)
            ?: throw NoSuchElementException("Customer with id ${requestDto.customerId} not found")

        // 기존 orderTable 데이터 삭제 후 새로운 데이터로 업데이트
        order.orderTable.clear()
        val updatedOrderTables = requestDto.orderTables.map {
            val riceCakeType = productTypeRepository.findByIdOrNull(it.productId)
                ?: throw NoSuchElementException("RiceCakeType with id ${it.productId} not found")
            OrderTable(
                id = 0,
                quantity = it.quantity,
                productType = riceCakeType,
            )
        }.toMutableList()

        order.orderTable.addAll(updatedOrderTables)

        order.customer = customer
        order.pickupDate = requestDto.pickupDate
        order.isPaid = requestDto.isPaid
        order.hasRice = requestDto.hasRice
        order.isPickedUp = requestDto.isPickedUp
        order.totalPrice = updatedOrderTables.sumOf { it.productType.price.multiply(BigDecimal(it.quantity)) }

        val updatedOrder = orderRepository.save(order)
        return OrderRes.from(updatedOrder)
    }

    @Transactional(readOnly = true)
    override fun getOrdersByCustomer(customerId: Long, pageable: Pageable): Page<OrderRes> {
        return orderRepository.findAllByCustomerId(customerId, pageable)
            .map { OrderRes.from(it) }
    }
    @Transactional(readOnly = true)
    override fun getAllOrders(pageable: Pageable): Page<OrderListRes> {
        return orderRepository.findAll(pageable)
            .map { OrderListRes.from(it) } // 👈 변경된 DTO 적용
    }

    @Transactional
    override fun deleteOrders(ids: List<Long>) {
        val ordersToDelete = orderRepository.findAllByIdIn(ids)
        if (ordersToDelete.size != ids.size) {
            val foundIds = ordersToDelete.map { it.id }.toSet()
            val notFoundIds = ids.filter { it !in foundIds }
            throw NoSuchElementException("Orders with ids $notFoundIds not found")
        }
        orderRepository.deleteAll(ordersToDelete)
    }

    private fun generateOrderUid(): String {
        return "ORD-${UUID.randomUUID().toString().substring(0, 8)}"
    }
}