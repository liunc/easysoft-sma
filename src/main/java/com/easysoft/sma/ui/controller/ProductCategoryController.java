package com.easysoft.sma.ui.controller;

import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product-category")
public class ProductCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping("/index")
	public String index(Model model) {

		logger.info("product category index.");
		return "productcategory/index";
	}

	@RequestMapping("/add")
	public String add(Model model) {

		logger.info("product category add.");
		return "productcategory/add";
	}

	@RequestMapping("/edit_{id}")
	public String edit(Model model, @PathVariable String id) {
		
		ProductCategoryDetailResponse response = this.productCategoryService.find(id);
		model.addAttribute("vm", response);
		return "productcategory/edit";
	}

	@RequestMapping("/detail_{id}")
	public String detail(Model model, @PathVariable String id) {
		ProductCategoryDetailResponse response = this.productCategoryService.find(id);
		model.addAttribute("vm", response);
		return "productcategory/detail";
	}
}