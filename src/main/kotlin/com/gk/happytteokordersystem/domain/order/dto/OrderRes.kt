package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.Order
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class OrderRes(
    val orderId: Long,
    val orderUid: String,
    val customerId: Long?, // 추가된 필드
    val orderTables: List<OrderTableRes>,
    val totalPrice: BigDecimal,
    val createdAt: OffsetDateTime,
    val memo: String?,
    val pickupDate: LocalDateTime,
    val isPaid: Boolean,
    val hasRice: Boolean,
    val isPickedUp: Boolean,
    val isAllDay: Boolean
) {
    companion object {
        fun from(order: Order): OrderRes {
            return OrderRes(
                orderId = order.id,
                orderUid = order.orderUid,
                customerId = order.customer?.id, // 고객 ID 포함
                orderTables = order.orderTable.map { OrderTableRes.from(it) },
                totalPrice = order.totalPrice,
                createdAt = order.orderDate,
                memo = order.memo,
                pickupDate = order.pickupDate,
                isPaid = order.isPaid,
                hasRice = order.hasRice,
                isPickedUp = order.isPickedUp,
                isAllDay = order.isAllDay
            )
        }
    }
}