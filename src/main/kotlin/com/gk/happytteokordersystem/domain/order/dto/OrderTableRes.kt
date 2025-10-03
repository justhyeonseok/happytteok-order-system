package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.OrderTable
import java.math.BigDecimal

data class OrderTableRes(
    val id: Long,
    val productId: Long,
    val quantity: Int,
    val productName: String,
    val productPrice: BigDecimal,
    val productUnit: String,
    val hasRice: Boolean?
) {
    companion object {
        fun from(orderTable: OrderTable): OrderTableRes {
            val unit = orderTable.unit ?: "kg"
            val unitPrice = orderTable.productType.getPriceForUnit(unit) ?: BigDecimal.ZERO
            return OrderTableRes(
                id = orderTable.id,
                productId = orderTable.productType.id,
                quantity = orderTable.quantity,
                productName = orderTable.productType.name,
                productPrice = unitPrice,
                productUnit = unit,
                hasRice = orderTable.hasRice
            )
        }
    }
}