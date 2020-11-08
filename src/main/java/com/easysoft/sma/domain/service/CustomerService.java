package com.easysoft.sma.domain.service;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerAddressDetailResponse;
import com.easysoft.sma.domain.dto.CustomerAddressPageRequest;
import com.easysoft.sma.domain.dto.CustomerAddressPageRow;
import com.easysoft.sma.domain.dto.CustomerAddressUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRow;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageRow;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;
import com.easysoft.sma.domain.dto.TransactionAddRequest;
import com.easysoft.sma.domain.dto.TransactionPageRequest;
import com.easysoft.sma.domain.dto.TransactionPageRow;
import com.easysoft.sma.domain.entity.Customer;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface CustomerService {
    
    public void addCustomerCategory(CustomerCategoryAddRequest request) throws BusinessException;

	public void updateCustomerCategory(CustomerCategoryUpdateRequest request) throws BusinessException;

    public void changeCustomerCategoryStatus(String id) throws BusinessException;

	public void deleteCustomerCategory(String id) throws BusinessException;

	public CustomerCategoryDetailResponse findCustomerCategoryById(String id) throws BusinessException;

	public List<TextValueObject> findCustomerCategoryByStatus(String status);

	public PageResponse<CustomerCategoryPageRow> findCustomerCategoryByPage(Pageable pageable, CustomerCategoryPageRequest request);
	
	public Customer addCustomer(CustomerAddRequest request) throws BusinessException;
	
	public void existsCustomer(String id) throws BusinessException;

    public void updateCustomer(CustomerUpdateRequest request)throws BusinessException;

    public void changeCustomerStatus(String id) throws BusinessException;
    
    public void deleteCustomer(String id) throws BusinessException;

    public CustomerDetailResponse findCustomerById(String id) throws BusinessException;

    public PageResponse<CustomerPageRow> findCustomerByPage(Pageable pageable, CustomerPageRequest request);

	public PageResponse<CustomerAddressPageRow> findCustomerAddressByPage(Pageable pageable, CustomerAddressPageRequest request);

	public List<CustomerAddressDetailResponse> findAddress(String customerId, String category);

	public CustomerAddressDetailResponse findCustomerAddressById(String id) throws BusinessException;

	public void createOrUpdateCustomerAddress(String customerId, String category, String linkman, String telephone,
			String address);

	public void updateCustomerAddress(CustomerAddressUpdateRequest request) throws BusinessException;

	public void deleteCustomerAddress(String id);

	public PageResponse<TransactionPageRow> findTransactionByPage(Pageable pageable, TransactionPageRequest request);
	
	public void recharge(TransactionAddRequest request)
			throws BusinessException;

	public void consume(TransactionAddRequest request) throws BusinessException;
}










