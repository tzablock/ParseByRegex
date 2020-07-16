package com.parsebyregex.controller;

import com.parsebyregex.constants.SeparationCharacter;
import com.parsebyregex.repository.KeyValuePairsRepository;
import com.parsebyregex.service.CommonStringParsingService;
import com.parsebyregex.service.datavalidation.DataValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.InfoProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commonstring")
public class CommonStringController { //TODO it test
    private CommonStringParsingService parsingService;
    private DataValidatorService validatorService;
    private KeyValuePairsRepository repository;

    @Autowired
    public CommonStringController(CommonStringParsingService parsingService, DataValidatorService validatorService, KeyValuePairsRepository repository) {
        this.parsingService = parsingService;
        this.validatorService = validatorService;
        this.repository = repository;
    }

    @GetMapping("")
    public String instructions() {
        return "/comma - parse key value pairs separated by comma\n" +
                "option 1: \"key1,value1,key2,value2\"\n" +
                "option 2: \"key1,value1,key2,value2\",\"key1,value1,key2,value2\"\n";
    }

    @PostMapping("/comma")
    ResponseEntity<String> separatedByComma(@RequestBody List<String> stringsToParse){
        validatorService.validateForException(() -> {
            Map<String, List<String>> keyValuePars = parsingService.parseForOneCharacterSeparation(stringsToParse, SeparationCharacter.COMMA);
            repository.saveOrUpdate(keyValuePars);
        });
        return validatorService.getResponse();
    }
}
