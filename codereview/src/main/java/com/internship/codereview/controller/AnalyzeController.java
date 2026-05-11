package com.internship.codereview.controller;

import com.internship.codereview.dto.RequestDto;
import com.internship.codereview.dto.ResponseDto;
import com.internship.codereview.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;
    @PostMapping
    public ResponseEntity<?> analyzeCode(@RequestBody RequestDto requestDto){
        ResponseDto res=analyzeService.analyze(requestDto);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
