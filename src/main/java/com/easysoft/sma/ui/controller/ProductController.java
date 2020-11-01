package com.easysoft.sma.ui.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.service.ProductService;
import com.easysoft.sma.domain.valueobject.DeliveryMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private LocalMessageSource messageSource;

    private List<String> builderSalesYears() {

        LocalDate date = LocalDate.now();
        int year = date.getYear();
        List<String> salesYears = new ArrayList<String>();
        for (int i = year; i > year - 20; i--) {
            salesYears.add(String.valueOf(i));
        }
        return salesYears;
    }

    @RequestMapping("/index")
    public String getProductIndex(Model model) {

        List<TextValueObject> categories = this.productService.findProductCategoryByStatus(ZeroOne.ONE);
        model.addAttribute("categories", categories);

        model.addAttribute("salesYears", this.builderSalesYears());

        List<TextValueObject> deliveryModes = new ArrayList<TextValueObject>();
        for (String mode : DeliveryMode.all()) {
            deliveryModes.add(new TextValueObject(this.messageSource.getMessage("delivery_mode_" + mode), mode));
        }
        model.addAttribute("deliveryModes", deliveryModes);
        logger.info("product index.");
        return "product/index";
    }

    @RequestMapping("/add")
	public String getProductAdd(Model model) {

		List<TextValueObject> categories = this.productService.findProductCategoryByStatus(ZeroOne.ONE);
        model.addAttribute("categories", categories);

		model.addAttribute("salesYears", this.builderSalesYears());

		List<TextValueObject> deliveryModes = new ArrayList<TextValueObject>();
        for (String mode : DeliveryMode.all()) {
            deliveryModes.add(new TextValueObject(this.messageSource.getMessage("delivery_mode_" + mode), mode));
        }
		model.addAttribute("deliveryModes", deliveryModes);
        
		return "product/add";
	}

	@RequestMapping("/edit_{id}")
	public String getProductEdit(Model model, @PathVariable String id) {
		
		List<TextValueObject> deliveryModes = new ArrayList<TextValueObject>();
        for (String mode : DeliveryMode.all()) {
            deliveryModes.add(new TextValueObject(this.messageSource.getMessage("delivery_mode_" + mode), mode));
        }
		model.addAttribute("deliveryModes", deliveryModes);
		
		ProductDetailResponse response = this.productService.findProductById(id);
		model.addAttribute("vm", response);
		return "product/edit";
	}

	@RequestMapping("/detail_{id}")
	public String getProductDetail(Model model, @PathVariable String id) {

		ProductDetailResponse response = this.productService.findProductById(id);
		
		List<String> deliveryModes = new ArrayList<String>();
        for (String mode : response.getSupportDeliveryMode().split(",")) {
            deliveryModes.add(this.messageSource.getMessage("delivery_mode_" + mode));
        }
        response.setSupportDeliveryMode(String.join(",", deliveryModes));
		model.addAttribute("vm", response);
		return "product/detail";
	}
	
	@RequestMapping("/category_index")
	public String getProductCategoryIndex(Model model) {

		logger.info("product category index.");
		return "product/category_index";
	}

	@RequestMapping("/category_add")
	public String getProductCategoryAdd(Model model) {

		return "product/category_add";
	}

	@RequestMapping("/category_edit_{id}")
	public String getProductCategoryEdit(Model model, @PathVariable String id) {
	
		ProductCategoryDetailResponse response = this.productService.findProductCategoryById(id);
		model.addAttribute("vm", response);
		return "product/category_edit";
	}

	@RequestMapping("/category_detail_{id}")
	public String getProductCategoryDetail(Model model, @PathVariable String id) {
 
		ProductCategoryDetailResponse response = this.productService.findProductCategoryById(id);
		model.addAttribute("vm", response);
		return "product/category_detail";
	}

}
