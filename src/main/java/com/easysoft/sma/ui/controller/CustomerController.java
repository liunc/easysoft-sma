package com.easysoft.sma.ui.controller;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.domain.dto.CustomerAddressDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.service.CustomerService;
import com.easysoft.sma.domain.valueobject.TransactionSource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LocalMessageSource messageSource;

	@RequestMapping("/category_index")
	public String getCustomerCategoryIndex(Model model) {

		logger.info("customer category index.");
		return "customer/category_index";
	}

	@RequestMapping("/category_add")
	public String getCustomerCategoryAdd(Model model) {

		logger.info("customer category add.");
		return "customer/category_add";
	}

	@RequestMapping("/category_edit_{id}")
	public String getCustomerCategoryEdit(Model model, @PathVariable String id) {

		CustomerCategoryDetailResponse response = this.customerService.findCustomerCategoryById(id);
		model.addAttribute("vm", response);
		return "customer/category_edit";
	}

	@RequestMapping("/category_detail_{id}")
	public String getCustomerCategoryDetail(Model model, @PathVariable String id) {
		CustomerCategoryDetailResponse response = this.customerService.findCustomerCategoryById(id);
		model.addAttribute("vm", response);
		return "customer/category_detail";
	}

	@RequestMapping("/index")
	public String getCustomerIndex(Model model) {

		List<TextValueObject> categories = this.customerService.findCustomerCategoryByStatus("");
		model.addAttribute("categories", categories);

		return "customer/index";
	}

	@RequestMapping("/add")
	public String getCustomerAdd(Model model) {

		List<TextValueObject> categories = this.customerService.findCustomerCategoryByStatus(ZeroOne.ONE);
		model.addAttribute("categories", categories);

		return "customer/add";
	}

	@RequestMapping("/edit_{id}")
	public String getCustomerEdit(Model model, @PathVariable String id) {

		CustomerDetailResponse response = this.customerService.findCustomerById(id);
		model.addAttribute("vm", response);

		List<TextValueObject> categories = this.customerService.findCustomerCategoryByStatus("");
		model.addAttribute("categories", categories);

		return "customer/edit";
	}

	@RequestMapping("/detail_{id}")
	public String getCustomerDetail(Model model, @PathVariable String id) {
		CustomerDetailResponse response = this.customerService.findCustomerById(id);
		model.addAttribute("vm", response);
		return "customer/detail";
	}

	@RequestMapping("/recharge_{id}")
	public String getCustomerRecharge(Model model, @PathVariable String id) {

		String[] rechargeType = TransactionSource.recharge();
		List<TextValueObject> transactionSource = new ArrayList<TextValueObject>();
		for (int i = 0; i < rechargeType.length; i++) {
			transactionSource.add(new TextValueObject(
					this.messageSource.getMessage("transaction_source_" + rechargeType[i]), rechargeType[i]));
		}

		model.addAttribute("transactionSource", transactionSource);
		model.addAttribute("customerId", id);

		return "customer/recharge";
	}

	@RequestMapping("/address")
	public String getCustomerAddress(Model model) {

		return "customer/address";
	}

	@RequestMapping("/address_edit_{id}")
	public String getCustomerAddressEdit(Model model, @PathVariable String id) {

		CustomerAddressDetailResponse response = this.customerService.findCustomerAddressById(id);
		model.addAttribute("vm", response);

		return "customer/address_edit";
	}

	@RequestMapping("/transaction")
	public String getTransaction(Model model) {

		String[] all = TransactionSource.all();
		List<TextValueObject> transactionSources = new ArrayList<TextValueObject>();
		for (int i = 0; i < all.length; i++) {
			transactionSources
					.add(new TextValueObject(this.messageSource.getMessage("transaction_source_" + all[i]), all[i]));
		}

		model.addAttribute("transactionSources", transactionSources);
		return "customer/transaction";
	}

}