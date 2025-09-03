package com.gk.happytteokordersystem.domain.order.model

import com.gk.happytteokordersystem.domain.customer.model.Customer
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "order_uid", unique = true, nullable = false)
    val orderUid: String,

    // customer_id가 null을 허용하도록 `nullable = true`로 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    var customer: Customer?, // customer 필드도 nullable로 변경

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderTable: MutableList<OrderTable>,

    @Column(name = "total_price", nullable = false)
    val totalPrice: BigDecimal,

    @Column(name = "pickup_date", nullable = false)
    val pickupDate: LocalDateTime,

    @Column(name = "is_paid", nullable = false)
    var isPaid: Boolean = false,

    @Column(name = "has_rice", nullable = false)
    var hasRice: Boolean = false,

    @Column(name = "is_picked_up", nullable = false)
    var isPickedUp: Boolean = false,

    @Column(name = "order_date", nullable = false)
    val orderDate: OffsetDateTime = OffsetDateTime.now()
)