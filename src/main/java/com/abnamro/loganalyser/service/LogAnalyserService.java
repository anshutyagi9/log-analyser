package com.abnamro.loganalyser.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogAnalyserService {
    public List<String> getLogs(String logType){
        StringBuffer sb = new StringBuffer();
        List<String> exceptions = new ArrayList<>();
        List<String> logs = readLogsFromLogFile(logType, sb);
        logs.forEach(log->{
            int index = log.indexOf("Exception:");
            while(index>0){
                if(log.charAt(index-1)!=' '){
                    int lastIndex = log.substring(0,index+1).lastIndexOf('.');
                    String exp;
                    if(lastIndex!=-1){
                         exp = log.substring(lastIndex+1, index+"Exception".length());
                    }else {
                        lastIndex = log.substring(0,index+1).lastIndexOf(" ");
                        exp = log.substring(lastIndex+1, index+"Exception".length());
                    }

                    exceptions.add(exp);
                    index = log.indexOf("Exception:", index+1);
                }
            }
        });

        return countExceptions(exceptions);
    }

    private static List<String> readLogsFromLogFile(String logType, StringBuffer sb) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/logFile-2018-09-10.log"));
            String s;
            while((s=reader.readLine())!=null){
                sb.append(s);
            }
            reader.close();
        }catch (Exception ex){

        }
        return getString(sb.toString(),logType);
    }

    private static List<String> getString(String s, String logType){
        List<String> logs = new ArrayList<>();
        int index = s.indexOf(logType);
         while(index !=-1){
             String sc;
            int lastIndex = indexOfRegEx(s.substring(index+logType.length()));
            if(lastIndex!=-1){
                 sc= s.substring(index,index+lastIndex);
            }else{
                sc= s.substring(index);
            }

            logs.add(sc);
            index = s.indexOf(logType, index+1);
        }
        return  logs;
    }

    private static int indexOfRegEx(String strSource) {
        int index = -1;
        Pattern p =  Pattern.compile("(DEBUG|ERROR|INFO|WARN)");
        Matcher m = p.matcher(strSource);
        if(m.find()) {
            index = m.start();
        }
        return index;

    }

    private static List<String> countExceptions(List<String> list) {
        Map<String, Integer> exceptions = new HashMap<>();
        List<String> exceptionsList = new ArrayList<>();
        list.forEach(error -> {
            if (exceptions.containsKey(error)) {
                exceptions.put(error, exceptions.get(error)+1);
            } else {
                exceptions.put(error, 1);
            } });
         Map<String, Integer> sortedByOccurrence = exceptions.entrySet()
                 .stream()
                 .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

         sortedByOccurrence.forEach((K,V)->{
            exceptionsList.add(K+" has occurred "+V +" times");

        });
        return exceptionsList;
    }

}
