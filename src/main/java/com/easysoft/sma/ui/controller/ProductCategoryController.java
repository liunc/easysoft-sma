package com.easysoft.sma.ui.controller;

import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.service.ProductCategoryService;
import com.easysoft.sma.ui.model.BootstrapTableResponse;
import com.easysoft.sma.ui.model.ProductCategoryBootstrapTableRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public BootstrapTableResponse<ProductCategoryPageResponse> categeoryList(@RequestBody ProductCategoryBootstrapTableRequest request) {

		BootstrapTableResponse<ProductCategoryPageResponse> response = new BootstrapTableResponse<ProductCategoryPageResponse>();

		Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
		if (StringUtils.hasText(request.getSort())) {
			pageable = PageRequest.of(request.getPage(), request.getLimit(),
					request.isDesc() ? Direction.DESC : Direction.ASC, request.getSort());
		}

		PageResponse<ProductCategoryPageResponse> entities = this.productCategoryService.page(pageable,
				new ProductCategoryPageRequest(request.getName(), request.getStatus()));
		if (entities == null || entities.getResults() == null) {
			return response;
		}
		response.setRows(entities.getResults());
		response.setTotal(entities.getTotal());

		return response;
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