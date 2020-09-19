package com.easysoft.sma.domain.service;

import java.util.List;

import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;

public interface CustomerCategoryService {

	public void add(CustomerCategoryAddRequest input) throws BusinessException;

	public void update(CustomerCategoryUpdateRequest input) throws BusinessException;

	public void delete(String id) throws BusinessException;

	public CustomerCategoryDetailResponse find(String id) throws BusinessException;

	public List<TextValueObject> findAll();

	public PageResponse<CustomerCategoryPageResponse> page(CustomerCategoryPageRequest input);
}
