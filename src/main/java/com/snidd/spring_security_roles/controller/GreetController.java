package com.snidd.spring_security_roles.controller;

import com.snidd.spring_security_roles.model.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greet")
public class GreetController {


    /**
     * Get stored authenticated user from SecurityContextHolder.
     * @return printed username.
     */
    @GetMapping
    public String greet() {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hi " + user.getUsername() + ", you are allowed.";
    }


    @PostMapping("/update")
    public String updateResource() {
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hi " + user.getUsername() + ", you are allowed to update the resource.";
    }



 }
