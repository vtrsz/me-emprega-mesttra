package com.mesttra.meemprega.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mesttra.meemprega.entity.Job;
import com.mesttra.meemprega.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class ApiController {

    @Autowired
    private SearchService searchService;

    @GetMapping ("/api/search")
    public ResponseEntity<ArrayList<Job>> send(@RequestParam final String keywords) throws JsonProcessingException {
        ArrayList<Job> searchResult = searchService.searchAndGetApi(keywords);
        return new ResponseEntity<ArrayList<Job>>(searchResult, HttpStatus.OK);
    }
}
