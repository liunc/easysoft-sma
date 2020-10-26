package com.easysoft.sma.api.controller;

import java.util.List;

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
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;
import com.easysoft.sma.domain.service.ProductCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "产品分类接口")
@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryRestController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@ApiOperation("根据状态获取产品分类数据")
	@RequestMapping(value = "/{status}", method = RequestMethod.GET)
	public List<TextValueObject> getCategory(@PathVariable String status) {
		return this.productCategoryService.findAll(status);
	}

	@ApiOperation("根据Id获取某条产品分类数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProductCategoryDetailResponse getOne(@PathVariable String id) throws BusinessException {
		return this.productCategoryService.find(id);
	}

	@ApiOperation("分页获取产品分类数据")
	@RequestMapping(method = RequestMethod.GET)
	public PageResponse<ProductCategoryPageResponse> page(@PageableDefault Pageable pageable,
			ProductCategoryPageRequest request) {
		return this.productCategoryService.page(pageable, request);
	}

	@ApiOperation("新增产品分类数据")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody @Valid ProductCategoryAddRequest request) throws BusinessException {
		 this.productCategoryService.add(request);
	}

	@ApiOperation("根据Id更新产品分类数据")
	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestBody ProductCategoryUpdateRequest request) throws BusinessException {
		this.productCategoryService.update(request);
	}

	@ApiOperation("根据Id更新产品分类状态")
	@RequestMapping(value="/{id}", method = RequestMethod.PATCH)
	public void changeStatus(@PathVariable String id) throws BusinessException {
		this.productCategoryService.changeStatus(id);
	}

	@ApiOperation("根据Id删除产品分类数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) throws BusinessException {
		this.productCategoryService.delete(id);
	}
}
