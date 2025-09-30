package com.gk.happytteokordersystem.domain.customer.model

import com.gk.happytteokordersystem.domain.order.model.Order
import com.gk.happytteokordersystem.global.model.BaseTimeEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener::class)
class Customer(
    @Column(name = "name")
    var name: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "memo")
    var memo: String?
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders: MutableList<Order> = mutableListOf()



//    @CreatedDate
//    @Column(name = "created_at", updatable = false)
//    val createdAt: LocalDateTime = LocalDateTime.now()
//
//    @LastModifiedDate
//    @Column(name = "updated_at")
//    var updatedAt: LocalDateTime = LocalDateTime.now()
}


