package com.gk.happytteokordersystem.domain.customer.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "phone")
    val phone: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime

    )


