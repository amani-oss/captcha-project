package com.example.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @GetMapping("/new")
    public Map<String, String> newCaptcha() throws Exception {
        String text = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(text);
        String id = UUID.randomUUID().toString();
        cache.put(id, text);
        
        return Map.of(
            "captchaId", id,
            "imageUrl", "/captcha/image/" + id
        );
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String id) throws Exception {
        if (!cache.containsKey(id)) {
            throw new RuntimeException("CAPTCHA not found");
        }
        
        String text = cache.get(id);
        BufferedImage image = captchaProducer.createImage(text);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody Map<String, String> data) {
        String id = data.get("captchaId");
        String answer = data.get("captchaCode");
        
        if (id == null || answer == null) {
            return Map.of("success", false);
        }
        
        boolean ok = cache.containsKey(id) && cache.get(id).equalsIgnoreCase(answer);
        if (ok) {
            cache.remove(id);
        }
        return Map.of("success", ok);
    }
}
