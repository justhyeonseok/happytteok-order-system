package com.gk.happytteokordersystem.domain.order.controller

import com.gk.happytteokordersystem.domain.order.dto.OrderCreateReq
import com.gk.happytteokordersystem.domain.order.dto.OrderListRes
import com.gk.happytteokordersystem.domain.order.dto.OrderRes
import com.gk.happytteokordersystem.domain.order.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "주문 관리")
@RequestMapping("/api-v1/orders")
class OrderController(private val orderService: OrderService) {


    @PostMapping
    @Operation(summary = "주문생성")
    fun createOrder(@RequestBody requestDto: OrderCreateReq): ResponseEntity<OrderRes> {
        val response = orderService.createOrder(requestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @Operation(summary = "주문서 단건 조회")
    @GetMapping("/{orderId}")
    fun getOrderDetails(@PathVariable orderId: Long): ResponseEntity<OrderRes> {
        val response = orderService.getOrderDetails(orderId)
        return ResponseEntity.ok(response)
    }
    @GetMapping
    @Operation(summary = "전체 주문 목록 조회 (페이지네이션)")
    fun getAllOrders(
        @PageableDefault(sort = ["orderDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<OrderListRes>> { // 👈 변경된 DTO 적용
        val response = orderService.getAllOrders(pageable)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "주문서 수정")
    fun updateOrder(@PathVariable orderId: Long, @RequestBody requestDto: OrderCreateReq): ResponseEntity<OrderRes> {
        val response = orderService.updateOrder(orderId, requestDto)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/by-customer/{customerId}")
    @Operation(summary = "고객 주문서 단건 조회")
    fun getOrdersByCustomer(
        @PathVariable customerId: Long,
        @PageableDefault(sort = ["orderDate"]) pageable: Pageable
    ): ResponseEntity<Page<OrderRes>> {
        val response = orderService.getOrdersByCustomer(customerId, pageable)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping
    @Operation(summary = "주문서 삭제")
    fun deleteOrders(@RequestParam ids: List<Long>): ResponseEntity<Void> {
        orderService.deleteOrders(ids)
        return ResponseEntity.noContent().build()
    }
}