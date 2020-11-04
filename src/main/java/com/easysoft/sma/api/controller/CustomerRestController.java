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
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户分类接口")
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@ApiOperation("根据状态获取客户分类数据")
	@RequestMapping(value = "/categories/{status}", method = RequestMethod.GET)
	public List<TextValueObject> getCustomerCategoryByStatus(@PathVariable String status) {
		return this.customerService.findCustomerCategoryByStatus(status);
	}

	@ApiOperation("根据Id获取某条客户分类数据")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public CustomerCategoryDetailResponse getCustomerCategoryById(@PathVariable String id) throws BusinessException {
		return this.customerService.findCustomerCategoryById(id);
	}

	@ApiOperation("分页获取客户分类数据")
	@RequestMapping(value="/categories", method = RequestMethod.GET)
	public PageResponse<CustomerCategoryPageResponse> getCustomerCategoryByPage(@PageableDefault Pageable pageable,
			CustomerCategoryPageRequest request) {
		return this.customerService.findCustomerCategoryByPage(pageable, request);
	}

	@ApiOperation("新增客户分类数据")
	@RequestMapping(value="/categories",method = RequestMethod.POST)
	public void addCustomerCategory(@RequestBody @Validated CustomerCategoryAddRequest request) throws BusinessException {
		this.customerService.addCustomerCategory(request);
	}

	@ApiOperation("根据Id更新客户分类数据")
	@RequestMapping(value="/categories",method = RequestMethod.PUT)
	public void updateCustomerCategory(@RequestBody @Validated CustomerCategoryUpdateRequest request) throws BusinessException {
		this.customerService.updateCustomerCategory(request);
	}

	@ApiOperation("根据Id更新客户分类状态")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PATCH)
	public void changeCustomerCategoryStatus(@PathVariable String id) throws BusinessException {
		this.customerService.changeCustomerCategoryStatus(id);
	}

	@ApiOperation("根据Id删除客户分类数据")
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public void deleteCustomerCategory(@PathVariable String id) throws BusinessException {
		this.customerService.deleteCustomerCategory(id);
	}
}
