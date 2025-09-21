package com.gk.happytteokordersystem.domain.customer.service

import com.gk.happytteokordersystem.domain.customer.dto.CustomerDetailRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerListRes
import com.gk.happytteokordersystem.domain.customer.dto.CustomerReq
import com.gk.happytteokordersystem.domain.customer.model.Customer
import com.gk.happytteokordersystem.domain.customer.repository.CustomerRepository
import com.gk.happytteokordersystem.global.exception.exceptions.ExistModelException
import com.gk.happytteokordersystem.global.exception.exceptions.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : CustomerService {
    override fun createCustomer(req: CustomerReq): CustomerDetailRes {
        // 해당 전화번호에 값이 있는경우 에러
        val getCustomer = customerRepository.findByPhone(req.phoneNumber)
        if (getCustomer != null) {
            throw ExistModelException(req.phoneNumber)
        }

        val customer = customerRepository.save(Customer(name = req.name, phone = req.phoneNumber, memo = req.memo))
        return CustomerDetailRes(
            name = customer.name,
            phoneNumber = customer.phone,
            createdAt = customer.createdAt,
            updatedAt = customer.updatedAt,
            memo = customer.memo,
            id = customer.id
        )
    }

    override fun searchCustomers(name: String?, phone: String?, pageable: Pageable): Page<CustomerListRes> {
        val customersPage = if (!name.isNullOrBlank() || !phone.isNullOrBlank()) {
            val searchTerm = if (!name.isNullOrBlank()) name else phone ?: ""
            customerRepository.findByNameOrPhoneContaining(searchTerm, pageable)
        } else {
            customerRepository.findAll(pageable)
        }
        return customersPage.map {
            CustomerListRes(
                id = it.id,
                name = it.name,
                phoneNumber = it.phone,
                memo = it.memo
            )
        }
    }

    override fun getCustomersWithOrderCount(pageable: Pageable): Page<CustomerListRes> {
        // 이 메서드는 주문 수량을 기준으로 정렬된 페이지를 반환합니다.
        val customersPage = customerRepository.findAllByOrderByOrderCountDesc(pageable)
        return customersPage.map {
            CustomerListRes(
                id = it.id,
                name = it.name,
                phoneNumber = it.phone,
                memo = it.memo
            )
        }
    }
    override fun getCustomerById(id: Long): CustomerDetailRes {
        val customer = customerRepository.findById(id)
            .orElseThrow { ModelNotFoundException(id.toString()) }
        return CustomerDetailRes(
            name = customer.name,
            phoneNumber = customer.phone,
            createdAt = customer.createdAt,
            updatedAt = customer.updatedAt,
            memo = customer.memo,
            id = customer.id
        )
    }

    override fun updateCustomer(id: Long, req: CustomerReq): CustomerDetailRes {
        val customer = customerRepository.findById(id)
            .orElseThrow { ModelNotFoundException(id.toString()) }

        customer.name = req.name
        if(customerRepository.findByPhone(req.phoneNumber) != null) {
            throw ExistModelException(req.phoneNumber)
        }
        customer.phone = req.phoneNumber

        val updatedCustomer = customerRepository.save(customer)
        return CustomerDetailRes(
            name = updatedCustomer.name,
            phoneNumber = updatedCustomer.phone,
            createdAt = updatedCustomer.createdAt,
            updatedAt = updatedCustomer.updatedAt,
            memo = updatedCustomer.memo,
            id = customer.id
        )
    }

    override fun deleteCustomer(id: Long) {
        customerRepository.findByIdOrNull(id) ?: throw ModelNotFoundException(id.toString())
        customerRepository.deleteById(id)
    }
}