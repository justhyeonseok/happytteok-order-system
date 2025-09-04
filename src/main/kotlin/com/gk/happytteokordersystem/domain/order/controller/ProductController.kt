package com.gk.happytteokordersystem.domain.order.controller

import com.gk.happytteokordersystem.domain.order.dto.CreateProductReq
import com.gk.happytteokordersystem.domain.order.dto.CreateProductRes
import com.gk.happytteokordersystem.domain.order.dto.ProductRes
import com.gk.happytteokordersystem.domain.order.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "품목 관리")
@RequestMapping("api-vi")
class ProductController(
    private val productService: ProductService // 서비스 이름 변경
) {

    @PostMapping("/products")
    @Operation(summary = "상품 추가")
    fun createProduct(
        @RequestBody createProductReq: CreateProductReq
    ): ResponseEntity<CreateProductRes> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(productService.createProduct(createProductReq))
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "상품 수정")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody updateProductReq: CreateProductReq
    ): ResponseEntity<ProductRes> {
        val response = productService.updateProduct(productId, updateProductReq)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "상품 상세 조회")
    fun getProductDetails(@PathVariable productId: Long): ResponseEntity<ProductRes> {
        val response = productService.getProductDetails(productId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회 (페이지네이션)")
    fun getAllProducts(
        @PageableDefault(sort = ["name"]) pageable: Pageable
    ): ResponseEntity<Page<ProductRes>> {
        val response = productService.getAllProducts(pageable)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/products/{productId}/toggle-status")
    @Operation(summary = "상품 상태 변경 (활성화/비활성화)")
    fun toggleProductStatus(@PathVariable productId: Long): ResponseEntity<Void> {
        productService.toggleProductStatus(productId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Void> {
        productService.deleteProduct(productId)
        return ResponseEntity.noContent().build()
    }
}