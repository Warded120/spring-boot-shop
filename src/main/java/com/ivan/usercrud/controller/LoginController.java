package com.ivan.usercrud.controller;

import com.ivan.usercrud.entity.Product;
import com.ivan.usercrud.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    ProductService productService;

    @Autowired
    public LoginController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String discover(Model theModel) {

        List<Product> products = productService.findInterestingProducts();

        theModel.addAttribute("products", products);

        return "main/discover";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login-page";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied/access-denied";
    }
}
