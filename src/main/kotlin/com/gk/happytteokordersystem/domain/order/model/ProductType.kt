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

    // 단위별 가격 정보
    @Column(name = "price_per_kg")
    val pricePerKg: BigDecimal?,

    @Column(name = "price_per_doe")
    val pricePerDoe: BigDecimal?,

    @Column(name = "price_per_mal")
    val pricePerMal: BigDecimal?,

    @Column(name = "price_per_piece")
    val pricePerPiece: BigDecimal?,

    @Column(name = "price_per_pack")
    val pricePerPack: BigDecimal?,

    @Column(name = "active")
    var isActive: Boolean,

) {
    // 기본 단위 (가격이 설정된 첫 번째 단위)
    fun getDefaultUnit(): String {
        return when {
            pricePerKg != null -> "kg"
            pricePerDoe != null -> "되"
            pricePerMal != null -> "말"
            pricePerPiece != null -> "개"
            pricePerPack != null -> "팩"
            else -> "kg"
        }
    }

    // 특정 단위의 가격을 가져오는 함수
    fun getPriceForUnit(unit: String): BigDecimal? {
        return when (unit) {
            "kg" -> pricePerKg
            "되" -> pricePerDoe
            "말" -> pricePerMal
            "개" -> pricePerPiece
            "팩" -> pricePerPack
            else -> null
        }
    }
}