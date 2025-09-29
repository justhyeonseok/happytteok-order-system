package com.gk.happytteokordersystem.domain.order.dto

import com.gk.happytteokordersystem.domain.order.model.ProductType
import java.math.BigDecimal

data class ProductRes(
    val id: Long,
    val name: String,
    val pricePerKg: BigDecimal?,
    val pricePerDoe: BigDecimal?,
    val pricePerMal: BigDecimal?,
    val pricePerPiece: BigDecimal?,
    val pricePerPack: BigDecimal?,
    val isActive: Boolean
) {
    companion object {
        fun from(productType: ProductType): ProductRes {
            return ProductRes(
                id = productType.id,
                name = productType.name,
                pricePerKg = productType.pricePerKg,
                pricePerDoe = productType.pricePerDoe,
                pricePerMal = productType.pricePerMal,
                pricePerPiece = productType.pricePerPiece,
                pricePerPack = productType.pricePerPack,
                isActive = productType.isActive
            )
        }
    }
}
