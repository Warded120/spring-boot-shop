package com.ivan.usercrud.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${user.roles}")
    List<String> roles;

    @Value("${location.types}")
    List<String> locationTypes;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model, HttpServletRequest request) {

        System.out.println("in global exception handler");

        BindingResult bindingResult = ex.getBindingResult();

        // Capture errors and add to model
        model.addAttribute("org.springframework.validation.BindingResult.user", bindingResult);

        model.addAttribute("user", bindingResult.getTarget());
        model.addAttribute("roles", roles);  // You need to fetch these roles
        model.addAttribute("locationTypes", locationTypes);  // You need to fetch these roles
        model.addAttribute("regType", request.getParameter("regType")); // get regType from request

        // Return the user form view
        return "user/user-form";
    }
}
