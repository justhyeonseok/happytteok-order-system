package com.gk.happytteokordersystem.domain.order.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "rice_type")
class RiceCakeType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "price")
    val price: BigDecimal,

    @Column(name = "unit")
    val unit: String,

    @Column(name = "active")
    val isActive: Boolean,

) {
    @JoinColumn(name = "order_table_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val orderTable: OrderTable? = null
}