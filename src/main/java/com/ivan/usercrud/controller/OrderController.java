package com.ivan.usercrud.controller;

import com.ivan.usercrud.entity.Order;
import com.ivan.usercrud.entity.Product;
import com.ivan.usercrud.entity.User;
import com.ivan.usercrud.service.order.OrderService;
import com.ivan.usercrud.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("order") Order order, Model theModel) {

        if (order == null) {
            System.out.println("order is null");
            return "redirect:/orders/error/orderNullError";
        } else {
            System.out.println("Order is not null");
        }

        // Fetch the product from the database to ensure it is managed
        // "detached entity passed to persist" error solution
        Product product = productService.findById(order.getProduct().getId());

        if (product == null) {
            System.out.println("Product not found");
            return "redirect:/orders/error/productNotFound";
        }

        User user = order.getUser();

        if (product.getAmount() < order.getAmount()) {
            return "redirect:/orders/error/notEnoughAmount";
        }

        product.setAmount(product.getAmount() - order.getAmount());

        order.setTotalPrice((int)product.getPrice() * order.getAmount());

        // Save the order and update the product amount
        order.setProduct(product);
        orderService.save(order);

        return "redirect:/orders/success/" + order.getProduct().getName();
    }


    @GetMapping("/success/{productName}")
    public String success(@PathVariable String productName, Model theModel) {

        theModel.addAttribute("productName", productName);

        return "product/product-purchase-confirmation";
    }

    @GetMapping("/error/{errorName}")
    public String error(@PathVariable String errorName, Model model) {

        model.addAttribute("errorName", errorName);

        return "order/order-error-page";
    }
}
