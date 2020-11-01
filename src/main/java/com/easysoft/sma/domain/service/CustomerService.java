package com.easysoft.sma.domain.service;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageResponse;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface CustomerService {
    
    public String addCustomer(CustomerAddRequest request) throws BusinessException;

    public void updateCustomer(CustomerUpdateRequest request)throws BusinessException;

    public void deleteCustomer(String id) throws BusinessException;

    public CustomerDetailResponse findCustomerById(String id) throws BusinessException;

    public PageResponse<CustomerPageResponse> findCustomerByPage(Pageable pageable, CustomerPageRequest request);

    public void addCustomerCategory(CustomerCategoryAddRequest request) throws BusinessException;

	public void updateCustomerCategory(CustomerCategoryUpdateRequest request) throws BusinessException;

    public void changeCustomerCategoryStatus(String id) throws BusinessException;

	public void deleteCustomerCategory(String id) throws BusinessException;

	public CustomerCategoryDetailResponse findCustomerCategoryById(String id) throws BusinessException;

	public List<TextValueObject> findCustomerCategoryByStatus();

	public PageResponse<CustomerCategoryPageResponse> findCustomerCategoryByPage(Pageable pageable, CustomerCategoryPageRequest request);
}










