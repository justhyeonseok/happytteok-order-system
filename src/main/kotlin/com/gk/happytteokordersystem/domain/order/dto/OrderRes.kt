package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.Order
import java.math.BigDecimal
import java.time.OffsetDateTime

data class OrderRes(
    val orderId: Long,
    val orderUid: String,
    val orderTables: List<OrderTableRes>,
    val totalPrice: BigDecimal,
    val createdAt: OffsetDateTime,
    val memo: String
) {
    companion object {
        fun from(order: Order): OrderRes {
            return OrderRes(
                orderId = order.id,
                orderUid = order.orderUid,
                orderTables = order.orderTable.map { OrderTableRes.from(it) },
                totalPrice = order.totalPrice,
                createdAt = order.orderDate,
                memo = order.memo
            )
        }
    }
}