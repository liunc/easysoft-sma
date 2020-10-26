package com.easysoft.sma.ui.controller;

import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.service.CustomerCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer-category")
public class CustomerCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerCategoryController.class);

	@Autowired
	private CustomerCategoryService customerCategoryService;

	@RequestMapping("/index")
	public String index(Model model) {

		logger.info("customer category index.");
		return "customercategory/index";
	}

	@RequestMapping("/add")
	public String add(Model model) {

		logger.info("customer category add.");
		return "customercategory/add";
	}

	@RequestMapping("/edit_{id}")
	public String edit(Model model, @PathVariable String id) {
		
		CustomerCategoryDetailResponse response = this.customerCategoryService.find(id);
		model.addAttribute("vm", response);
		return "customercategory/edit";
	}

	@RequestMapping("/detail_{id}")
	public String detail(Model model, @PathVariable String id) {
		CustomerCategoryDetailResponse response = this.customerCategoryService.find(id);
		model.addAttribute("vm", response);
		return "customercategory/detail";
	}
}