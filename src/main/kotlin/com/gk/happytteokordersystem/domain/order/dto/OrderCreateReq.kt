package com.gk.happytteokordersystem.domain.order.dto

import java.time.LocalDateTime

data class OrderCreateReq(
    val customerId: Long,
    val orderTables: List<OrderTableReq>,
    val pickupDate: LocalDateTime,
    val isPaid: Boolean = false,
    val hasRice: Boolean = false,
    val isPickedUp: Boolean = false
)