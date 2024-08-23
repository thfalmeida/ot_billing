package com.oteasy.ot_billing.service;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.User;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class LoginService {

    @Autowired
    Dotenv dotenv;
    
    public ResponseEntity<String> login(User user){
        if(user.login.equals(dotenv.get("LOGIN")) && user.password.equals(dotenv.get("PASSWORD"))){
            return new ResponseEntity<String>("Login with success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Login or password incorrect", HttpStatus.BAD_REQUEST);
    }
}
