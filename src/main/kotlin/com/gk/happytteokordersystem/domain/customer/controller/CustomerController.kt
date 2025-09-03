package com.gk.happytteokordersystem.domain.customer.controller

import com.gk.happytteokordersystem.domain.customer.dto.CustomerDetailRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerListRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerReq
import com.gk.happytteokordersystem.domain.customer.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api-vi")
class CustomerController(private val customerService: CustomerService) {
    @PostMapping("/customers")
    @Operation(summary = "고객 생성")
    fun createCustomer(
        @RequestBody req: CustomerReq): ResponseEntity<CustomerDetailRes> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(customerService.createCustomer(req))
    }
    @GetMapping("/customers")
    @Operation(summary = "고객 검색 또는 모든 고객 조회 ")
    fun getCustomers(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) phone: String?,
        @PageableDefault(size = 10, sort = ["name"]) pageable: Pageable
    ): ResponseEntity<Page<CustomerListRes>> {
        return ResponseEntity.ok(customerService.searchCustomers(name, phone, pageable))
    }

    @GetMapping("/customers/by-order-count")
    @Operation(summary = "주문량 순으로 고객 목록 조회")
    fun getCustomersByOrderCount(
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<CustomerListRes>> {
        return ResponseEntity.ok(customerService.getCustomersWithOrderCount(pageable))
    }
    @GetMapping("/customers/{id}")
    @Operation(summary = "단일 고객 조회")
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<CustomerDetailRes> {
        return ResponseEntity.ok(customerService.getCustomerById(id))
    }
    @PutMapping("/customers/{id}")
    @Operation(summary = "고객 정보 수정")
    fun updateCustomer(
        @PathVariable id: Long,
        @RequestBody req: CustomerReq
    ): ResponseEntity<CustomerDetailRes> {
        return ResponseEntity.ok(customerService.updateCustomer(id, req))
    }
    @DeleteMapping("/customers/{id}")
    @Operation(summary = "고객 삭제")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.deleteCustomer(id)
        return ResponseEntity.noContent().build()
    }
}