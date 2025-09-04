package com.gk.happytteokordersystem.domain.order.dto

data class OrderTableReq(
    val productId: Long,
    val quantity: Int,
    val unit: String
)