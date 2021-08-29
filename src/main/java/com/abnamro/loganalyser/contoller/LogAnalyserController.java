package com.abnamro.loganalyser.contoller;

import com.abnamro.loganalyser.service.LogAnalyserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "logs")
public class LogAnalyserController {
    @Autowired
    LogAnalyserService service;

    @GetMapping
    public ResponseEntity<List<String>> getLogs(@RequestParam String logType) {
        return new ResponseEntity<>(service.getLogs(logType), HttpStatus.OK);
    }
}
