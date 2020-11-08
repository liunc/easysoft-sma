package com.easysoft.sma.domain.service;


import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.ProductAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageRow;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.dto.ProductPageRequest;
import com.easysoft.sma.domain.dto.ProductPageRow;
import com.easysoft.sma.domain.dto.ProductUpdateRequest;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ProductService {

    public void addProduct(ProductAddRequest request) throws BusinessException;

	public void updateProduct(ProductUpdateRequest request) throws BusinessException;

	public void changeProductStatus(String id) throws BusinessException;

	public void deleteProduct(String id) throws BusinessException;

	public ProductDetailResponse findProductById(String id) throws BusinessException;

	public PageResponse<ProductPageRow> findProductByPage(Pageable pageable, ProductPageRequest request);
	
	public void addProductCategory(ProductCategoryAddRequest request) throws BusinessException;

	public void updateProductCategory(ProductCategoryUpdateRequest request) throws BusinessException;

	public void changeProductCategoryStatus(String id) throws BusinessException;

	public void deleteProductCategory(String id) throws BusinessException;

	public ProductCategoryDetailResponse findProductCategoryById(String id) throws BusinessException;

	public List<TextValueObject> findProductCategoryByStatus(String status);

	public PageResponse<ProductCategoryPageRow> findProductCategoryByPage(Pageable pageable, ProductCategoryPageRequest request);
}