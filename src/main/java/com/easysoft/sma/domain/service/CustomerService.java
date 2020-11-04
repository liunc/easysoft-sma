package com.easysoft.sma.domain.service;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerAddressDetailResponse;
import com.easysoft.sma.domain.dto.CustomerAddressPageRequest;
import com.easysoft.sma.domain.dto.CustomerAddressPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageResponse;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;
import com.easysoft.sma.domain.dto.TransactionRecordPageResponse;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

public interface CustomerService {
    
    public void addCustomerCategory(CustomerCategoryAddRequest request) throws BusinessException;

	public void updateCustomerCategory(CustomerCategoryUpdateRequest request) throws BusinessException;

    public void changeCustomerCategoryStatus(String id) throws BusinessException;

	public void deleteCustomerCategory(String id) throws BusinessException;

	public CustomerCategoryDetailResponse findCustomerCategoryById(String id) throws BusinessException;

	public List<TextValueObject> findCustomerCategoryByStatus(String status);

	public PageResponse<CustomerCategoryPageResponse> findCustomerCategoryByPage(Pageable pageable, CustomerCategoryPageRequest request);
	
	public void addCustomer(CustomerAddRequest request) throws BusinessException;
	
	public void existsCustomer(String id) throws BusinessException;

    public void updateCustomer(CustomerUpdateRequest request)throws BusinessException;

    public void changeCustomerStatus(String id) throws BusinessException;
    
    public void deleteCustomer(String id) throws BusinessException;

    public CustomerDetailResponse findCustomerById(String id) throws BusinessException;

    public PageResponse<CustomerPageResponse> findCustomerByPage(Pageable pageable, CustomerPageRequest request);

	public PageResponse<CustomerAddressPageResponse> findAddress(Pageable pageable, CustomerAddressPageRequest request);

	public List<CustomerAddressDetailResponse> findAddress(String customerId, String category);

	public CustomerAddressDetailResponse findAddressById(String id) throws BusinessException;

	public void createOrUpdateAddress(String customerId, String category, String linkman, String telephone,
			String address);

	public void updateAddress(String id, String linkman, String telephone, String address) throws BusinessException;

	public void deleteAddress(String id);

	public PageResponse<TransactionRecordPageResponse> findTransactionRecordByPage(String wechatName, String name, String category,
			Pageable pageable);

	public void recharge(String customerId, String transactionSource, BigDecimal amount, String remark)
			throws BusinessException;

	public void consume(String customerId, BigDecimal amount, String remark) throws BusinessException;
}










