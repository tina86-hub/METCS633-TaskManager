package com.team4.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team4.taskmanager.model.User;
import com.team4.taskmanager.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

	@Autowired
    private UserService userService;

  
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "forms/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/register";
        }

        if (userService.isUserEmailPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "register";
        }

        userService.createUser(user);
        return "views/success";
    }
    
    @PostMapping("/rest/register")
    public String registerUser(@RequestBody User user) {
        
    	 if (userService.isUserEmailPresent(user.getEmail())) {
    		  return "User Exists";
    		 
    	 }else {
    		  userService.createUser(user);
    		  return "User registered successfully";
    	 }
       
    }

}
