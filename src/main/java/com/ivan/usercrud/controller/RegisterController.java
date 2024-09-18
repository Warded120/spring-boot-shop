package com.ivan.usercrud.controller;

import com.ivan.usercrud.entity.User;
import com.ivan.usercrud.service.userData.UserDataService;
import com.ivan.usercrud.service.user.UserService;
import com.ivan.usercrud.template.UserTemplate;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    UserService userService;
    UserDataService userDataService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${location.types}")
    List<String> locationTypes;

    @Value("${user.roles}")
    List<String> roles;

    @Autowired
    public RegisterController(UserService userService, UserDataService userDataService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userDataService = userDataService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/show")
    public String show(Model theModel) {

        theModel.addAttribute("user", new UserTemplate());
        theModel.addAttribute("roles", roles);
        theModel.addAttribute("locationTypes", locationTypes);
        theModel.addAttribute("regType", "reg");

        return "user/user-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") UserTemplate userTemplate,
                       @RequestParam(name = "regType") String regType,
                       HttpSession session,
                       BindingResult theBindingResult,
                       Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "user/user-form";
        }

        if (regType.equals("reg") || regType.equals("add")) {
            User existing = userService.findByUsername(userTemplate.getUsername());

            if (existing != null) {

                theModel.addAttribute("Error", "This username is already taken");

                theModel.addAttribute("user", userTemplate);
                theModel.addAttribute("roles", roles);
                theModel.addAttribute("regType", "reg");

                return "user/user-form";
            }

            if (!userTemplate.getPassword().equals(userTemplate.getConfirmPassword())) {
                theModel.addAttribute("Error", "Passwords do not match");

                theModel.addAttribute("user", userTemplate);
                theModel.addAttribute("roles", roles);
                theModel.addAttribute("regType", "reg");

                return "user/user-form";
            }

            System.out.println("success");

            User newUser = new User(userTemplate, bCryptPasswordEncoder);

            userService.save(newUser);

            if(regType.equals("reg")) {
                session.setAttribute("user", newUser);

                theModel.addAttribute("successMessage", "User: " + newUser.getUsername() +" has been registered successfully");
                theModel.addAttribute("regType", "reg");

                return "user/user-form-confirmation";
            }

            theModel.addAttribute("successMessage", "User: " + newUser.getUsername() + " has been created successfully");
            theModel.addAttribute("regType", "add");

            return "user/user-form-confirmation";

        } else if (regType.equals("update")) {
            // check if the user exists
            User existing = userService.findByUsername(userTemplate.getUsername());

            if (existing == null) {
                theModel.addAttribute("userTemplate", userTemplate);
                theModel.addAttribute("registrationError", "Username must exist.");
                return "user/user-form";
            }

            // create an updated user
            User updatedUser = new User(userTemplate, bCryptPasswordEncoder);

            // save updated user
            System.out.println("userData id: " + updatedUser.getUserData().getId());
            userDataService.save(updatedUser.getUserData());

            // place updated user in the web HTTP session for later use
            session.setAttribute("user", updatedUser);

            theModel.addAttribute("successMessage", "User details have been updated successfully");
            theModel.addAttribute("regType", "update");

            return "user/user-form-confirmation";

        } else {
            theModel.addAttribute("userTemplate", new UserTemplate());
            theModel.addAttribute("registrationError", "Invalid registration type.");
            return "user/user-form";
        }
    }
}
