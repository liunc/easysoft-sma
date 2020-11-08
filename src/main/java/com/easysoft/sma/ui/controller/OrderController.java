package com.easysoft.sma.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.domain.service.CustomerService;
import com.easysoft.sma.domain.service.OrderService;
import com.easysoft.sma.domain.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LocalMessageSource messageSource;
    
    @RequestMapping("/select_customer")
    public String selectCustomer(Model model) {

        List<TextValueObject> categories = this.customerService.findCustomerCategoryByStatus(ZeroOne.ONE);
        model.addAttribute("categories", categories);
        return "order/select_customer";
    }
}
