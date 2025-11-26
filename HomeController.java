package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🎯 CAPTCHA Application is Running!<br><br>" +
               "Available Endpoints:<br>" +
               "• <a href='/captcha/new'>/captcha/new</a> - Generate new CAPTCHA<br>" +
               "• <a href='/captcha/verify'>/captcha/verify</a> - Verify CAPTCHA<br>" +
               "• <a href='/health'>/health</a> - Health check";
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Application is running!";
    }
}
