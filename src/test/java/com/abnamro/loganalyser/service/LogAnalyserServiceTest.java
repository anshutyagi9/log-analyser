package com.abnamro.loganalyser.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class LogAnalyserServiceTest {

    @InjectMocks
    LogAnalyserService service= new LogAnalyserService();

    @Test
    void testGetLogs() {
        List<String> ex=  service.getLogs("ERROR");
        assertEquals(9, ex.size());
        List<String> ex1=  service.getLogs("DEBUG");
        assertEquals(0, ex1.size());
        List<String> ex2=  service.getLogs("INFO");
        assertEquals(0, ex2.size());
        List<String> ex3=  service.getLogs("WARN");
        assertEquals(7, ex3.size());
    }
}