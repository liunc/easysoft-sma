package com.easysoft.sma.ui.controller;

import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerCategoryController.class);

	@Autowired
	private CustomerService customerService;

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
	public String edit(Model model, @PathVariable String id) {
		
		CustomerCategoryDetailResponse response = this.customerService.findCustomerCategoryById(id);
		model.addAttribute("vm", response);
		return "customer/category_edit";
	}

	@RequestMapping("/category_detail_{id}")
	public String detail(Model model, @PathVariable String id) {
		CustomerCategoryDetailResponse response = this.customerService.findCustomerCategoryById(id);
		model.addAttribute("vm", response);
		return "customer/category_detail";
	}
}