package com.example.securingweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecuringWebController {

    @GetMapping("/")
    public String home() {
        return "home"; // Trả về trang home.html
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // Trả về trang hello.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về trang login.html
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // Trả về trang admin.html
    }
}
