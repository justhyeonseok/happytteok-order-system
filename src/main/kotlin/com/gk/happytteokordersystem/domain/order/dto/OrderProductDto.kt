package com.gk.happytteokordersystem.domain.order.dto

data class OrderProductDto(
    val productName: String,
    val quantity: Int,
    val unit: String?
)