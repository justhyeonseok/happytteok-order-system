package com.gk.happytteokordersystem.domain.order.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "product_type")
data class ProductType (
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
    var isActive: Boolean,

) {

}