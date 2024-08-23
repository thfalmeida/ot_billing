package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oteasy.ot_billing.model.User;
import com.oteasy.ot_billing.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;
    
    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody User user){
        return loginService.login(user);
    }
}
