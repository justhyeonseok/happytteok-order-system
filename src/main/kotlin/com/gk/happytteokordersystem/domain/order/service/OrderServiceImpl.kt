package com.gk.happytteokordersystem.domain.order.service

import com.gk.happytteokordersystem.domain.customer.repository.CustomerRepository
import com.gk.happytteokordersystem.domain.order.dto.OrderCreateReq
import com.gk.happytteokordersystem.domain.order.dto.OrderListRes
import com.gk.happytteokordersystem.domain.order.dto.OrderRes
import com.gk.happytteokordersystem.domain.order.dto.OrderUpdateReq
import com.gk.happytteokordersystem.domain.order.model.Order
import com.gk.happytteokordersystem.domain.order.model.OrderTable
import com.gk.happytteokordersystem.domain.order.repository.OrderRepository
import com.gk.happytteokordersystem.domain.order.repository.ProductTypeRepository
import com.gk.happytteokordersystem.global.exception.exceptions.ModelNotFoundException
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


    override fun createOrder(requestDto: OrderCreateReq): OrderRes {
        val customer = customerRepository.findByIdOrNull(requestDto.customerId)
            ?: throw ModelNotFoundException(requestDto.customerId.toString())

        val orderTables = requestDto.orderTables.map {
            val productType = productTypeRepository.findByIdOrNull(it.productId)
                ?: throw ModelNotFoundException(it.productId.toString())
            OrderTable(
                id = 0,
                quantity = it.quantity,
                unit = it.unit,
                hasRice = it.hasRice,
                productType = productType
            )
        }.toMutableList()

        val calculatedPrice = orderTables.sumOf { 
            val unitPrice = it.productType.getPriceForUnit(it.unit) ?: BigDecimal.ZERO
            unitPrice.multiply(BigDecimal(it.quantity)) 
        }
        val finalPrice = requestDto.finalPrice ?: calculatedPrice

        val newOrder = Order(
            id = 0,
            orderUid = generateOrderUid(),
            customer = customer,
            orderTable = orderTables,
            totalPrice = finalPrice,
            pickupDate = requestDto.pickupDate,
            isPaid = requestDto.isPaid,
            hasRice = requestDto.hasRice,
            isPickedUp = requestDto.isPickedUp,
            orderDate = OffsetDateTime.now(),
            memo = requestDto.memo
        )

        // orderTable에 order를 연결
        orderTables.forEach { it.order = newOrder }

        val savedOrder = orderRepository.save(newOrder)
        return OrderRes.from(savedOrder)
    }

    @Transactional(readOnly = true)
    override fun getOrderDetails(orderId: Long): OrderRes {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw ModelNotFoundException(orderId.toString())
        return OrderRes.from(order)
    }


    override fun updateOrder(orderId: Long, requestDto: OrderUpdateReq): OrderRes {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw ModelNotFoundException(orderId.toString())


        // 기존 orderTable 데이터 삭제 후 새로운 데이터로 업데이트
        order.orderTable.clear()
        val updatedOrderTables = requestDto.orderTables.map {
            val riceCakeType = productTypeRepository.findByIdOrNull(it.productId)
                ?: throw ModelNotFoundException(it.productId.toString())
            OrderTable(
                id = 0,
                quantity = it.quantity,
                unit = it.unit,
                hasRice = it.hasRice,
                productType = riceCakeType,
                order = order
            )
        }.toMutableList()

        order.orderTable.addAll(updatedOrderTables)
        order.pickupDate = requestDto.pickupDate
        order.isPaid = requestDto.isPaid
        order.hasRice = requestDto.hasRice
        order.isPickedUp = requestDto.isPickedUp

        val calculatedPrice = updatedOrderTables.sumOf { 
            val unitPrice = it.productType.getPriceForUnit(it.unit) ?: BigDecimal.ZERO
            unitPrice.multiply(BigDecimal(it.quantity)) 
        }
        val finalPrice = requestDto.finalPrice ?: calculatedPrice
        order.totalPrice = finalPrice
        order.memo = requestDto.memo

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
            .map { OrderListRes.from(it) }
    }


    override fun deleteOrders(ids: List<Long>) {
        val ordersToDelete = orderRepository.findAllByIdIn(ids)
        if (ordersToDelete.size != ids.size) {
            val foundIds = ordersToDelete.map { it.id }.toSet()
            val notFoundIds = ids.filter { it !in foundIds }
            throw ModelNotFoundException(notFoundIds.toString())
        }
        orderRepository.deleteAll(ordersToDelete)
    }

    private fun generateOrderUid(): String {
        return "ORD-${UUID.randomUUID().toString().substring(0, 8)}"
    }

    override fun paidStatus(id: Long) {
        val order = orderRepository.findByIdOrNull(id) ?: throw ModelNotFoundException(id.toString())
        val updateOrder=  order.copy(isPaid = !order.isPaid)
        orderRepository.save(updateOrder)
    }

    override fun pickUpStatus(id: Long) {
        val order = orderRepository.findByIdOrNull(id) ?: throw ModelNotFoundException(id.toString())
        val updateOrder=  order.copy(isPickedUp = !order.isPickedUp)
        orderRepository.save(updateOrder)
    }

    override fun riceStatus(id: Long) {
        val order = orderRepository.findByIdOrNull(id) ?: throw ModelNotFoundException(id.toString())
        val updateOrder=  order.copy(hasRice = !order.hasRice)
        orderRepository.save(updateOrder)
    }
}