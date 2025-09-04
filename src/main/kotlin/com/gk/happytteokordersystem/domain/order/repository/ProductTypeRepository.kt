package com.gk.happytteokordersystem.domain.order.repository

import com.gk.happytteokordersystem.domain.order.model.ProductType
import org.springframework.data.jpa.repository.JpaRepository

interface ProductTypeRepository: JpaRepository<ProductType, Long> {
}