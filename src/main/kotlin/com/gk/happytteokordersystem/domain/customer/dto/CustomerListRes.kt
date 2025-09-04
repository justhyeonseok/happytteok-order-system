package com.gk.happytteokordersystem.domain.customer.dto

data class CustomerListRes(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val orderCount: Long = 0,
    val memo: String?
)
