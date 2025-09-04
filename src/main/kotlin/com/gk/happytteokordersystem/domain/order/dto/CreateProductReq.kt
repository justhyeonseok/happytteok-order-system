package com.gk.happytteokordersystem.domain.order.dto

data class CreateProductReq(
    val name: String,
    val price: Int,
    val unit: String
)