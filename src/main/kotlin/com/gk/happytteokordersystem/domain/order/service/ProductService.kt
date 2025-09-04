package com.gk.happytteokordersystem.domain.order.service

import com.gk.happytteokordersystem.domain.order.dto.CreateProductReq
import com.gk.happytteokordersystem.domain.order.dto.CreateProductRes
import com.gk.happytteokordersystem.domain.order.dto.ProductRes
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun createProduct(request: CreateProductReq): CreateProductRes
    fun updateProduct(productId: Long, request: CreateProductReq): ProductRes
    fun getProductDetails(productId: Long): ProductRes
    fun getAllProducts(pageable: Pageable): Page<ProductRes>
    fun toggleProductStatus(productId: Long)
    fun deleteProduct(productId: Long)
}