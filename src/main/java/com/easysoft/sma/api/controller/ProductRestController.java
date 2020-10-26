package com.easysoft.sma.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.ProductAddRequest;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.dto.ProductPageRequest;
import com.easysoft.sma.domain.dto.ProductPageResponse;
import com.easysoft.sma.domain.dto.ProductUpdateRequest;
import com.easysoft.sma.domain.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "产品接口")
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@ApiOperation("根据Id获取某条产品数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProductDetailResponse getOne(@PathVariable String id) throws BusinessException {
		return this.productService.find(id);
	}

	@ApiOperation("分页获取产品数据")
	@RequestMapping(method = RequestMethod.GET)
	public PageResponse<ProductPageResponse> page(@PageableDefault Pageable pageable,
			ProductPageRequest request) {
		return this.productService.page(pageable, request);
	}

	@ApiOperation("新增产品数据")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody @Valid ProductAddRequest request) throws BusinessException {
		 this.productService.add(request);
	}

	@ApiOperation("根据Id更新产品分类数据")
	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestBody ProductUpdateRequest request) throws BusinessException {
		this.productService.update(request);
	}

	@ApiOperation("根据Id更新产品状态")
	@RequestMapping(value="/{id}", method = RequestMethod.PATCH)
	public void changeStatus(@PathVariable String id) throws BusinessException {
		this.productService.changeStatus(id);
	}

	@ApiOperation("根据Id删除产品数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) throws BusinessException {
		this.productService.delete(id);
	}
}
