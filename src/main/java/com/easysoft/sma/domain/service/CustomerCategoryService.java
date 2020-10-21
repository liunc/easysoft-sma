package com.easysoft.sma.domain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;

public interface CustomerCategoryService {

	public Object add(CustomerCategoryAddRequest request) throws BusinessException;

	public void update(CustomerCategoryUpdateRequest request) throws BusinessException;

	public void delete(String id) throws BusinessException;

	public CustomerCategoryDetailResponse find(String id) throws BusinessException;

	public List<TextValueObject> findAll();

	public PageResponse<CustomerCategoryPageResponse> page(Pageable pageable, CustomerCategoryPageRequest request);
}
