package com.letrasypapeles.backend.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/swagger-ui")
    public String redirectToCustomSwagger() {
        return "redirect:/index.html";
    }
}
