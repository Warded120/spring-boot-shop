package com.ivan.usercrud.controller;

import com.ivan.usercrud.entity.Order;
import com.ivan.usercrud.entity.User;
import com.ivan.usercrud.service.auth.AuthService;
import com.ivan.usercrud.service.order.OrderService;
import com.ivan.usercrud.service.user.UserService;
import com.ivan.usercrud.template.UserTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;
    AuthService authService;
    OrderService orderService;
    User currentUser;

    @Value("${user.roles}")
    List<String> roles;

    @Value("${location.types}")
    List<String> locationTypes;

    @Autowired
    public UserController(UserService userService, AuthService authService, OrderService orderService) {
        this.userService = userService;
        this.authService = authService;
        this.orderService = orderService;
    }

    @GetMapping("/user")
    public String managers(Model theModel) {

        UserDetails currectUserDetails = authService.getCurrentUser();

        System.out.println(currectUserDetails.getUsername());

        currentUser = userService.findByUsername(currectUserDetails.getUsername());

        System.out.println(currentUser);

        theModel.addAttribute("user", currentUser);

        return "user/user-page";
    }

    @GetMapping("/user/order-history")
    public String userOrderHistory(Model theModel) {

        UserDetails currentUserDetails = authService.getCurrentUser();

        System.out.println(currentUserDetails.getUsername());

        User user = userService.findByUsername(currentUserDetails.getUsername());

        System.out.println(user);

        List<Order> orderHistory = orderService.findByUsername(user.getUsername());

        theModel.addAttribute("user", user);
        theModel.addAttribute("orderHistory", orderHistory);

        return "user/user-order-history-page";
    }

    @GetMapping("/add")
    public String add(Model theModel) {
        theModel.addAttribute("user", new UserTemplate());
        theModel.addAttribute("roles", roles);
        theModel.addAttribute("locationTypes", locationTypes);
        theModel.addAttribute("regType", "add");

        return "user/user-form";
    }

    @GetMapping("/update")
    public String update(Model theModel) {
        currentUser = userService.findByUsername(authService.getCurrentUser().getUsername()); // get user from userDetails

        theModel.addAttribute("user", new UserTemplate(currentUser));
        theModel.addAttribute("roles", roles);
        theModel.addAttribute("locationTypes", locationTypes);
        theModel.addAttribute("regType", "update");

        return "user/user-form";
    }
}
