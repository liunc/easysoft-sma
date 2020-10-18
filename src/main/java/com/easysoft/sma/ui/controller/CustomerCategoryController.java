package com.easysoft.sma.ui.controller;

import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageQuery;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.service.CustomerCategoryService;
import com.easysoft.sma.ui.model.BootstrapTableResponse;
import com.easysoft.sma.ui.model.CustomerCategoryPageRequest;

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

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public BootstrapTableResponse<CustomerCategoryPageResponse> categeoryList(@RequestBody CustomerCategoryPageRequest request) {

		BootstrapTableResponse<CustomerCategoryPageResponse> response = new BootstrapTableResponse<CustomerCategoryPageResponse>();

		Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
		if (StringUtils.hasText(request.getSort())) {
			pageable = PageRequest.of(request.getPage(), request.getLimit(),
					request.isDesc() ? Direction.DESC : Direction.ASC, request.getSort());
		}

		PageResponse<CustomerCategoryPageResponse> entities = this.customerCategoryService.page(pageable,
				new CustomerCategoryPageQuery(request.getName()));
		if (entities == null || entities.getResults() == null) {
			return response;
		}
		response.setRows(entities.getResults());
		response.setTotal(entities.getTotal());

		return response;
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