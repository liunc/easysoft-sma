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
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerAddressPageRequest;
import com.easysoft.sma.domain.dto.CustomerAddressPageRow;
import com.easysoft.sma.domain.dto.CustomerAddressUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRow;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageRow;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;
import com.easysoft.sma.domain.dto.TransactionAddRequest;
import com.easysoft.sma.domain.dto.TransactionPageRequest;
import com.easysoft.sma.domain.dto.TransactionPageRow;
import com.easysoft.sma.domain.entity.Customer;
import com.easysoft.sma.domain.service.CustomerService;
import com.easysoft.sma.domain.valueobject.TransactionCategory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户接口")
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
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public PageResponse<CustomerCategoryPageRow> getCustomerCategoryByPage(@PageableDefault Pageable pageable,
			CustomerCategoryPageRequest request) {
		return this.customerService.findCustomerCategoryByPage(pageable, request);
	}

	@ApiOperation("新增客户分类数据")
	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public void addCustomerCategory(@RequestBody @Validated CustomerCategoryAddRequest request)
			throws BusinessException {
		this.customerService.addCustomerCategory(request);
	}

	@ApiOperation("根据Id更新客户分类数据")
	@RequestMapping(value = "/categories", method = RequestMethod.PUT)
	public void updateCustomerCategory(@RequestBody @Validated CustomerCategoryUpdateRequest request)
			throws BusinessException {
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

	@ApiOperation("分页获取客户数据")
	@RequestMapping(method = RequestMethod.GET)
	public PageResponse<CustomerPageRow> getCustomerByPage(@PageableDefault Pageable pageable,
			CustomerPageRequest request) {
		return this.customerService.findCustomerByPage(pageable, request);
	}

	@ApiOperation("新增客户数据")
	@RequestMapping(method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody @Validated CustomerAddRequest request) throws BusinessException {
		return this.customerService.addCustomer(request);
	}

	@ApiOperation("根据Id更新客户数据")
	@RequestMapping(method = RequestMethod.PUT)
	public void updateCustomer(@RequestBody @Validated CustomerUpdateRequest request) throws BusinessException {
		this.customerService.updateCustomer(request);
	}

	@ApiOperation("根据Id更新客户状态")
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public void changeCustomerStatus(@PathVariable String id) throws BusinessException {
		this.customerService.changeCustomerStatus(id);
	}

	@ApiOperation("根据Id删除客户数据")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable String id) throws BusinessException {
		this.customerService.deleteCustomer(id);
	}

	@ApiOperation("新增交易")
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public void addTransaction(@RequestBody @Validated TransactionAddRequest request) throws BusinessException {
		TransactionCategory.check(request.getCategory());
		if (TransactionCategory.RECHARGE.equals(request.getCategory())) {
			this.customerService.recharge(request);
		} else if (TransactionCategory.CONSUME.equals(request.getCategory())) {
			this.customerService.consume(request);
		}
	}
	
	@ApiOperation("分页获取客户地址数据")
	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public PageResponse<CustomerAddressPageRow> getCustomerAddressByPage(@PageableDefault Pageable pageable,
			CustomerAddressPageRequest request) {
		return this.customerService.findCustomerAddressByPage(pageable, request);
	}
	
	@ApiOperation("根据Id更新客户地址数据")
	@RequestMapping(value = "/addresses", method = RequestMethod.PUT)
	public void updateCustomerAddress(@RequestBody @Validated CustomerAddressUpdateRequest request)
			throws BusinessException {
		this.customerService.updateCustomerAddress(request);
	}
	
	@ApiOperation("根据Id删除客户地址数据")
	@RequestMapping(value = "/addresses/{id}", method = RequestMethod.DELETE)
	public void deleteCustomerAddress(@PathVariable String id) throws BusinessException {
		this.customerService.deleteCustomerAddress(id);
	}
	
	@ApiOperation("分页获取客户分类数据")
	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public PageResponse<TransactionPageRow> getTransactionByPage(@PageableDefault Pageable pageable,
			TransactionPageRequest request) {
		return this.customerService.findTransactionByPage(pageable, request);
	}
}
