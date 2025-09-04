package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.OrderTable
import java.math.BigDecimal

data class OrderTableRes(
    val id: Long,
    val quantity: Int,
    val productName: String,
    val productPrice: BigDecimal,
    val productUnit: String
) {
    companion object {
        fun from(orderTable: OrderTable): OrderTableRes {
            return OrderTableRes(
                id = orderTable.id,
                quantity = orderTable.quantity,
                productName = orderTable.productType.name,
                productPrice = orderTable.productType.price,
                productUnit = orderTable.productType.unit
            )
        }
    }
}