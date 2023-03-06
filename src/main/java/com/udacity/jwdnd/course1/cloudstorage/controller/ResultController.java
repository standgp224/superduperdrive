package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/result")
public class ResultController {
    @GetMapping
    public String getResultPage() {
        return "result";
    }
}
