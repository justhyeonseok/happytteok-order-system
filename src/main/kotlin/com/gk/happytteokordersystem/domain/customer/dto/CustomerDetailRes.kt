package com.gk.happytteokordersystem.domain.customer.dto

import java.time.LocalDateTime

data class CustomerDetailRes (
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val memo: String?,
)