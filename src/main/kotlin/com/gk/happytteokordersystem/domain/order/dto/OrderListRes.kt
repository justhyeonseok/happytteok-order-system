package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.Order
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class OrderListRes(
    val orderId: Long,
    val customerName: String,
    val orderDate: OffsetDateTime,
    val orderUid: String,
    val pickupDate: LocalDateTime,
    val hasRice: Boolean,
    val isPaid: Boolean,
    val isPickedUp: Boolean,
    val isAllDay: Boolean?,
    val memo: String?,
    val products: List<OrderProductDto>

) {
    companion object {
        fun from(order: Order): OrderListRes {
            return OrderListRes(
                orderId = order.id,
                customerName = order.customer?.name ?: "정보 없음",
                orderDate = order.orderDate,
                orderUid = order.orderUid,
                pickupDate = order.pickupDate,
                hasRice = order.hasRice,
                isPaid = order.isPaid,
                isPickedUp = order.isPickedUp,
                isAllDay = order.isAllDay,
                memo = order.memo,
                products = order.orderTable.map {
                    OrderProductDto(
                        productName = it.productType.name,
                        quantity = it.quantity,
                        unit = it.unit ?: "kg",
                        hasRice = it.hasRice
                    )
                }
            )
        }
    }
}