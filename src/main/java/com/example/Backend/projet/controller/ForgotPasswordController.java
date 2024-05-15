package com.example.Backend.projet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.projet.service.ForgotPasswordService;

@RestController
public class ForgotPasswordController {
    @Autowired
    private ForgotPasswordService service;

    @PostMapping("/forgot-password")
    public String forgotPass(@RequestParam String email){
        String response = service.forgotPass(email);

        if(!response.startsWith("Invalid")){
            response = "http://localhost:4200/reset-password?token=" + response;
        }
        return response;
    }

    @PutMapping("/reset-password")
    public String resetPass(@RequestParam String token, @RequestParam String password){
        return service.resetPass(token,password);
    }
}
