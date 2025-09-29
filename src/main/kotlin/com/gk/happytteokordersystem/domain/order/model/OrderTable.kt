package com.gk.happytteokordersystem.domain.order.model

import jakarta.persistence.*

@Entity
@Table(name = "order_table")
class OrderTable (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "unit")
    val unit: String,

    @Column(name = "has_rice")
    val hasRice: Boolean?,

    @JoinColumn(name = "product_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val productType: ProductType

    ) {
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var order: Order? = null

}