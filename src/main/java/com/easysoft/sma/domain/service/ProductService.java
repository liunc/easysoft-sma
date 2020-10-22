package com.easysoft.sma.domain.service;

import java.util.List;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.ProductAddRequest;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.dto.ProductPageRequest;
import com.easysoft.sma.domain.dto.ProductPageResponse;
import com.easysoft.sma.domain.dto.ProductUpdateRequest;

import org.springframework.data.domain.Pageable;

public interface ProductService {

    public void add(ProductAddRequest request) throws BusinessException;

	public void update(ProductUpdateRequest request) throws BusinessException;

	public void delete(String id) throws BusinessException;

	public ProductDetailResponse find(String id) throws BusinessException;

	public List<ProductPageResponse> findAvailable();

	public PageResponse<ProductPageResponse> page(Pageable pageable, ProductPageRequest request);
}