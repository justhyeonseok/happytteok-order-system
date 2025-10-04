package com.gk.happytteokordersystem.domain.order.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderCreateReq(
    val customerId: Long,
    val memo: String?,
    val orderTables: List<OrderTableReq>,
    val pickupDate: LocalDateTime,
    val isPaid: Boolean = false,
    val hasRice: Boolean = false,
    val isPickedUp: Boolean = false,
    val finalPrice: BigDecimal? = null,
    val isAllDay: Boolean = false // 하루종일 옵션
)