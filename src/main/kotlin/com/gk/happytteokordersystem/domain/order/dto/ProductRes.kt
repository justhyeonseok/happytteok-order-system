package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.ProductType
import java.math.BigDecimal

data class ProductRes(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val unit: String,
    val isActive: Boolean
) {
    companion object {
        fun from(productType: ProductType): ProductRes {
            return ProductRes(
                id = productType.id,
                name = productType.name,
                price = productType.price,
                unit = productType.unit,
                isActive = productType.isActive
            )
        }
    }
}
