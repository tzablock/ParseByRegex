package com.parsebyregex.controller;

import com.parsebyregex.constants.SeparationCharacter;
import com.parsebyregex.service.CommonStringParsingService;
import com.parsebyregex.service.datavalidation.DataValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.InfoProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commonstring")
public class CommonStringController {
    private CommonStringParsingService parsingService;
    private DataValidatorService validatorService;

    @Autowired
    public CommonStringController(CommonStringParsingService parsingService, DataValidatorService validatorService) {
        this.parsingService = parsingService;
        this.validatorService = validatorService;
    }

    @GetMapping("")
    public String instructions() {
        return "/comma - parse key value pairs separated by comma\n" +
                "option 1: \"key1,value1,key2,value2\"\n" +
                "option 2: \"key1,value1,key2,value2\",\"key1,value1,key2,value2\"\n";
    }

    @PostMapping("/comma")
    ResponseEntity<String> separatedByComma(@RequestBody List<String> stringsToParse){
        Map<String, List<String>> keyValuePars = stringsToParse.stream().map(
                s -> parsingService.parseForOneCharacterSeparation(s, SeparationCharacter.COMMA)
        ).reduce(mergeMaps()).get();
        //TODO use repository to write it
        return validatorService.getResponse();
    }

    private BinaryOperator<Map<String, List<String>>> mergeMaps() {
        return (m1, m2) -> Stream.of(m1,m2)
                                 .flatMap(m -> m.entrySet().stream())
                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeValues()));
    }

    private BinaryOperator<List<String>> mergeValues() {
        return (v1, v2) -> Stream.of(v1, v2).flatMap(List::stream).collect(Collectors.toList());
    }
}
