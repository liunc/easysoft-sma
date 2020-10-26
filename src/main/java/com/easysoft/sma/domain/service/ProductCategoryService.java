package com.easysoft.sma.domain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;

public interface ProductCategoryService {
    
    public void add(ProductCategoryAddRequest request) throws BusinessException;

	public void update(ProductCategoryUpdateRequest request) throws BusinessException;

	public void changeStatus(String id) throws BusinessException;

	public void delete(String id) throws BusinessException;

	public ProductCategoryDetailResponse find(String id) throws BusinessException;

	public List<TextValueObject> findAll(String status);

	public PageResponse<ProductCategoryPageResponse> page(Pageable pageable, ProductCategoryPageRequest request);
}