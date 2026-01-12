package com.accesses.administrative_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThymleafController {


    @GetMapping("/home")
    public String home(Model model){
        List<String> historico = List.of(
                "Mensagem enviada para 11999999999",
                "Mensagem enviada para 11888888888",
                "Mensagem enviada para 11999999999",
                "Mensagem enviada para 11888888888",
                "Mensagem enviada para 11999999999",
                "Mensagem enviada para 11888888888",
                "Mensagem enviada para 11999999999",
                "Mensagem enviada para 11888888888"
        );

        model.addAttribute("historico", historico);
        return "home";
    }

}
