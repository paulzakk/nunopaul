package com.nuno.controller;

import com.nuno.entity.Search;
import com.nuno.repository.SearchRepository;
import com.nuno.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    ParseService parseService;

    @Autowired
    SearchRepository searchRepository;

    @GetMapping(value="/v1/place", produces = "application/json; charset=UTF-8", params = "q")
    public String kakaosearch(@RequestParam ("q") String query, @RequestParam (required = false, defaultValue = "1") int page, @RequestParam (required = false, defaultValue = "15") int size ) throws Exception {

        String Result = parseService.getParse(query, page, size);

        searchRepository.save(new Search(query));

        return Result;
    }
}
