package com.easysoft.sma.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.ProductAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.dto.ProductPageRequest;
import com.easysoft.sma.domain.dto.ProductPageResponse;
import com.easysoft.sma.domain.dto.ProductUpdateRequest;
import com.easysoft.sma.domain.service.ProductCategoryService;
import com.easysoft.sma.domain.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "产品接口")
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;

	@ApiOperation("根据Id获取某条产品数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProductDetailResponse getProductById(@PathVariable String id) throws BusinessException {
		return this.productService.find(id);
	}

	@ApiOperation("分页获取产品数据")
	@RequestMapping(method = RequestMethod.GET)
	public PageResponse<ProductPageResponse> getProductByPage(@PageableDefault Pageable pageable,
			ProductPageRequest request) {
		return this.productService.page(pageable, request);
	}

	@ApiOperation("新增产品数据")
	@RequestMapping(method = RequestMethod.POST)
	public void addProduct(@RequestBody @Validated ProductAddRequest request) throws BusinessException {
		 this.productService.add(request);
	}

	@ApiOperation("根据Id更新产品分类数据")
	@RequestMapping(method = RequestMethod.PUT)
	public void updateProduct(@RequestBody @Validated ProductUpdateRequest request) throws BusinessException {
		this.productService.update(request);
	}

	@ApiOperation("根据Id更新产品状态")
	@RequestMapping(value="/{id}", method = RequestMethod.PATCH)
	public void changeProductStatus(@PathVariable String id) throws BusinessException {
		this.productService.changeStatus(id);
	}

	@ApiOperation("根据Id删除产品数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id) throws BusinessException {
		this.productService.delete(id);
	}
	
	@ApiOperation("根据状态获取产品分类数据")
	@RequestMapping(value = "/categories/{status}", method = RequestMethod.GET)
	public List<TextValueObject> getProductCategoryByStatus(@PathVariable String status) {
		return this.productCategoryService.findAll(status);
	}

	@ApiOperation("根据Id获取某条产品分类数据")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public ProductCategoryDetailResponse getProductCategoryById(@PathVariable String id) throws BusinessException {
		return this.productCategoryService.find(id);
	}

	@ApiOperation("分页获取产品分类数据")
	@RequestMapping(value="/categories", method = RequestMethod.GET)
	public PageResponse<ProductCategoryPageResponse> getProductCategoryByPage(@PageableDefault Pageable pageable,
			ProductCategoryPageRequest request) {
		return this.productCategoryService.page(pageable, request);
	}

	@ApiOperation("新增产品分类数据")
	@RequestMapping(value="/categories",method = RequestMethod.POST)
	public void addProductCategory(@RequestBody @Validated ProductCategoryAddRequest request) throws BusinessException {
		this.productCategoryService.add(request);
	}

	@ApiOperation("根据Id更新产品分类数据")
	@RequestMapping(value="/categories",method = RequestMethod.PUT)
	public void updateProductCategory(@RequestBody @Validated ProductCategoryUpdateRequest request) throws BusinessException {
		this.productCategoryService.update(request);
	}

	@ApiOperation("根据Id更新产品分类状态")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PATCH)
	public void changeProductCategoryStatus(@PathVariable String id) throws BusinessException {
		this.productCategoryService.changeStatus(id);
	}

	@ApiOperation("根据Id删除产品分类数据")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public void deleteProductCategory(@PathVariable String id) throws BusinessException {
		this.productCategoryService.delete(id);
	}
}
