package com.gk.happytteokordersystem.domain.customer.service

import com.gk.happytteokordersystem.domain.customer.dto.CustomerDetailRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerListRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomerService {
    fun createCustomer(req: CustomerReq): CustomerDetailRes
    fun searchCustomers(name: String?, phone: String?, pageable: Pageable): Page<CustomerListRes>
    fun getCustomersWithOrderCount(pageable: Pageable): Page<CustomerListRes>
    fun getCustomerById(id: Long): CustomerDetailRes
    fun updateCustomer(id: Long, req: CustomerReq): CustomerDetailRes
    fun deleteCustomer(id: Long)
}