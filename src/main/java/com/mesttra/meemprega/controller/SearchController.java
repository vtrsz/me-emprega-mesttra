package com.mesttra.meemprega.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mesttra.meemprega.entity.Job;
import com.mesttra.meemprega.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/")
    public String redirectToHomePage() {
        return "home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }

    @RequestMapping( "/search")
    public String send(@RequestParam String keyword, @RequestParam(value = "email", defaultValue = "null") String email, Model model) {
        ArrayList<Job> searchResult = searchService.search(keyword, email);
        model.addAttribute("jobs", searchResult);
        return "search";
    }

    @RequestMapping( "/docs")
    public String send() {
        return "docs";
    }
}