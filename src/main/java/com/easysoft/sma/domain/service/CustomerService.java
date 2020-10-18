package com.easysoft.sma.domain.service;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageResponse;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;

import org.springframework.data.domain.Pageable;

public interface CustomerService {
    
    public String add(CustomerAddRequest request) throws BusinessException;

    public void update(CustomerUpdateRequest request)throws BusinessException;

    public void delete(String id) throws BusinessException;

    public CustomerDetailResponse find(String id) throws BusinessException;

    public PageResponse<CustomerPageResponse> page(Pageable pageable, CustomerPageRequest request);

}