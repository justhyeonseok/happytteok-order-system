package com.gk.happytteokordersystem.domain.order.dto

import java.math.BigDecimal

data class CreateProductReq(
    val name: String,
    val pricePerKg: BigDecimal? = null,
    val pricePerDoe: BigDecimal? = null,
    val pricePerMal: BigDecimal? = null,
    val pricePerPiece: BigDecimal? = null,
    val pricePerPack: BigDecimal? = null
)