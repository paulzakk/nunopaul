package com.nuno.controller;

import com.nuno.repository.SearchCountRepository;
import com.nuno.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PopularSearchController {

    @Autowired
    SearchRepository searchRepository;

    @GetMapping("/v1/popular")
    public List<SearchCountRepository> popularSearch() throws Exception {
        return searchRepository.popularSearch();
    }
}
