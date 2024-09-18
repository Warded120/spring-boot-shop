package com.ivan.usercrud.controller;

import com.ivan.usercrud.entity.Order;
import com.ivan.usercrud.entity.Product;
import com.ivan.usercrud.entity.User;
import com.ivan.usercrud.entity.select.SelectForm;
import com.ivan.usercrud.service.auth.AuthService;
import com.ivan.usercrud.service.product.ProductService;
import com.ivan.usercrud.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private AuthService authService;
    private UserService userService;

    private List<Product> products;

    @Autowired
    public ProductController(ProductService theProductService, AuthService theAuthService, UserService theUserService) {
        productService = theProductService;
        authService = theAuthService;
        userService = theUserService;
    }

    @GetMapping("/main")
    public String showMain(Model theModel) {

        products = new ArrayList<>();
        products = productService.findAll();

        theModel.addAttribute("products", products);

        System.out.println("products" + products);

        theModel.addAttribute("selectForm", new SelectForm());
        return "/main/main-page";
    }

    @PostMapping("/main")
    public String showMainWithSort(@ModelAttribute(name = "selectForm") SelectForm selectForm
                                  ,Model theModel) {

        System.out.println("main page loaded");
        System.out.println("selectForm: " + selectForm);
        System.out.println();

        products = new ArrayList<>();
        products = productService.filterBy(selectForm.getSortType(), '%' + selectForm.getKeyword() + '%');

        theModel.addAttribute("products", products);
        theModel.addAttribute("selectForm", selectForm);
        return "main/main-page";
    }

    @GetMapping("/product")
    public String productPage(@RequestParam int productId, Model theModel) {
        Product product = productService.findById(productId);

        theModel.addAttribute("product", product);

        return "product/product-page";
    }

    @GetMapping("/add")
    public String addProduct(Model theModel) {
        theModel.addAttribute("product", new Product());
        theModel.addAttribute("action", "add");
        return "product/product-form";
    }

    @GetMapping("/update")
    public String updateProduct(@RequestParam int productId, Model theModel) {
        Product product = productService.findById(productId);
        theModel.addAttribute("product", product);
        theModel.addAttribute("action", "update");
        return "product/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product theProduct,
                              @RequestParam(name = "action") String action) {
        if (action.equals("add")) {
            theProduct.setId(0);
        }

        // set all text to lower case for better search
        theProduct.setName(theProduct.getName());
        theProduct.setCategory(theProduct.getCategory().toLowerCase());

        // save the product
        productService.save(theProduct);

        if (action.equals("add")) {
            return "redirect:/users/user";
        } else{
            return "redirect:/products/main";
        }
    }

    @GetMapping("delete")
    public String deleteProduct(@RequestParam int productId) {
        productService.deleteById(productId);

        return "redirect:/products/main";
    }

    @GetMapping("/purchase")
    public String purchase(@RequestParam int productId, Model theModel) {

        Product product = productService.findById(productId);
        User user = userService.findByUsername(authService.getCurrentUser().getUsername());

        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);

        theModel.addAttribute("order", order);
        theModel.addAttribute("product", product);
        theModel.addAttribute("user", user);

        return "product/product-purchase-form";
    }
}
