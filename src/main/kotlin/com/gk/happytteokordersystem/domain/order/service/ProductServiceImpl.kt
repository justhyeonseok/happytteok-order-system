package com.gk.happytteokordersystem.domain.order.service

import com.gk.happytteokordersystem.domain.order.dto.CreateProductReq
import com.gk.happytteokordersystem.domain.order.dto.CreateProductRes
import com.gk.happytteokordersystem.domain.order.dto.ProductRes
import com.gk.happytteokordersystem.domain.order.model.ProductType
import com.gk.happytteokordersystem.domain.order.repository.ProductTypeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class ProductServiceImpl(
    private val productTypeRepository: ProductTypeRepository
): ProductService {
    override fun createProduct(request: CreateProductReq): CreateProductRes {
        val newProductType = ProductType(
            id = 0,
            name = request.name,
            pricePerKg = request.pricePerKg,
            pricePerDoe = request.pricePerDoe,
            pricePerMal = request.pricePerMal,
            pricePerPiece = request.pricePerPiece,
            pricePerPack = request.pricePerPack,
            isActive = true
        )
        val savedProductType = productTypeRepository.save(newProductType)
        return CreateProductRes(
            id = savedProductType.id,
            name = savedProductType.name
        )
    }

    @Transactional
    override fun updateProduct(productId: Long, request: CreateProductReq): ProductRes {
        val product = productTypeRepository.findByIdOrNull(productId)
            ?: throw NoSuchElementException("Product with id $productId not found")

        val updatedProduct = product.copy(
            name = request.name,
            pricePerKg = request.pricePerKg,
            pricePerDoe = request.pricePerDoe,
            pricePerMal = request.pricePerMal,
            pricePerPiece = request.pricePerPiece,
            pricePerPack = request.pricePerPack
        )
        val savedProduct = productTypeRepository.save(updatedProduct)
        return ProductRes.from(savedProduct)
    }

    @Transactional(readOnly = true)
    override fun getProductDetails(productId: Long): ProductRes {
        val product = productTypeRepository.findByIdOrNull(productId)
            ?: throw NoSuchElementException("Product with id $productId not found")
        return ProductRes.from(product)
    }

    @Transactional(readOnly = true)
    override fun getAllProducts(pageable: Pageable): Page<ProductRes> {
        return productTypeRepository.findAll(pageable)
            .map { ProductRes.from(it) }
    }

    @Transactional
    override fun toggleProductStatus(productId: Long) {
        val product = productTypeRepository.findByIdOrNull(productId)
            ?: throw NoSuchElementException("Product with id $productId not found")
        val updatedProduct = product.copy(isActive = !product.isActive)
        productTypeRepository.save(updatedProduct)
    }

    @Transactional
    override fun deleteProduct(productId: Long) {
        if (!productTypeRepository.existsById(productId)) {
            throw NoSuchElementException("Product with id $productId not found")
        }
        productTypeRepository.deleteById(productId)
    }
}