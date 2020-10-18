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
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageQuery;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.service.CustomerCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户分类接口")
@RestController
@RequestMapping("/api/customer-categories")
public class CustomerCategoryRestController {

	@Autowired
	private CustomerCategoryService customerCategoryService;

	@ApiOperation("获取所有数据")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public List<TextValueObject> getAll() {
		return this.customerCategoryService.findAll();
	}

	@ApiOperation("按Id获取某条数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CustomerCategoryDetailResponse getOne(@PathVariable String id) throws BusinessException {
		return this.customerCategoryService.find(id);
	}

	@ApiOperation("分页获取所有数据")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public PageResponse<CustomerCategoryPageResponse> page(@PageableDefault Pageable pageable,
			CustomerCategoryPageQuery request) {
		return this.customerCategoryService.page(pageable, request);
	}

	@ApiOperation("新增数据")
	@RequestMapping(method = RequestMethod.POST)
	public void addCategory(@RequestBody @Valid CustomerCategoryAddRequest request) throws BusinessException {
		 this.customerCategoryService.add(request);
	}

	@ApiOperation("更新数据")
	@RequestMapping(method = RequestMethod.PUT)
	public void updateCategory(@RequestBody CustomerCategoryUpdateRequest request) throws BusinessException {
		this.customerCategoryService.update(request);
	}

	@ApiOperation("删除数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteCategory(@PathVariable String id) throws BusinessException {
		this.customerCategoryService.delete(id);
	}
}
