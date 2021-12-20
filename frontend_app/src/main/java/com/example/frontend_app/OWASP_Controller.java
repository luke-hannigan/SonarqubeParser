package com.example.frontend_app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OWASP_Controller {
    @GetMapping("/")
    public String owasp(Model model){
        model.addAttribute("tests", Application.textReader.owaspTestNames);
        model.addAttribute("results", Application.textReader.owaspTestResults);
        return "owasp";
    }
}