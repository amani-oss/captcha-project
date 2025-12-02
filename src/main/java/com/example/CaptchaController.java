package com.example.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class CaptchaController {

    @Autowired
    private Producer captchaProducer;

    @GetMapping("/captcha/new")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        String text = captchaProducer.createText();
        session.setAttribute("captcha", text);
        BufferedImage image = captchaProducer.createImage(text);

        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }
}
