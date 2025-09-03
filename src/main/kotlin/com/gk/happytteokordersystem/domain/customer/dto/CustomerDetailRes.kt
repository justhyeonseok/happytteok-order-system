package com.gk.happytteokordersystem.domain.customer.dto

import java.time.LocalDateTime

data class CustomerDetailRes (
    val name: String,
    val phoneNumber: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime? // 추가
)