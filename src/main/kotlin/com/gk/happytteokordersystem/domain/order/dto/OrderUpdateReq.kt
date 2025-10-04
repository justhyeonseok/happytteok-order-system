package com.gk.happytteokordersystem.domain.order.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderUpdateReq(
    val customerId: Long, // 고객 ID 추가
    val memo: String?,
    val orderTables: List<OrderTableReq>,
    val pickupDate: LocalDateTime,
    val isPaid: Boolean = false,
    val hasRice: Boolean = false,
    val isPickedUp: Boolean = false,
    val isAllDay: Boolean = false, // 하루종일 옵션 추가
    val finalPrice: BigDecimal? = null
)