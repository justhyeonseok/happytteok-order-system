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

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY)
    val riceType: RiceCakeType

    ) {
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order? = null

}