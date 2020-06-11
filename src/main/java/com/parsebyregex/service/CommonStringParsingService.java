package com.parsebyregex.service;

import com.parsebyregex.constants.SeparationCharacter;
import com.parsebyregex.service.datavalidation.DataValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommonStringParsingService { //TODO tests
    private DataValidatorService validatorService;

    @Autowired
    public CommonStringParsingService(DataValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public Map<String, List<String>> parseForOneCharacterSeparation(final List<String> stringsToParse, SeparationCharacter separationCharacter) {
        return stringsToParse.stream().map(
                s -> parseForOneCharacterSeparation(s, separationCharacter)
        ).reduce(mergeMaps()).orElse(new HashMap<>());
    }

    public Map<String, List<String>> parseForOneCharacterSeparation(String strToParse, SeparationCharacter separationCharacter) {
        validatorService.validateIfNotEmptyInput(strToParse);
        String separationValue = separationCharacter.getSeparator();
        List<String> keyValuePars = Arrays.asList(strToParse.split(separationValue));
        validatorService.validateIfInputCreateKeyValuePairs(keyValuePars);
        return keyValuePars.stream()
                .map(
                        pairCorrespondingKeyValuePairs()
                ).filter(
                        removeSeparatorsBetweenGroupedPairs()
                ).collect(
                        Collectors.toMap(getKey(), getValueAndWrapIntoList(), mergeValuesForTheSameKey())
                );
    }

    private BinaryOperator<Map<String, List<String>>> mergeMaps() {
        return (m1, m2) -> Stream.of(m1, m2)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeValues()));
    }

    private BinaryOperator<List<String>> mergeValues() {
        return (v1, v2) -> Stream.of(v1, v2).flatMap(List::stream).collect(Collectors.toList());
    }

    private Function<String, List<String>> pairCorrespondingKeyValuePairs() {
        List<String> bufferWithKey = new ArrayList<>();
        return (String nextKeyOrValue) -> {
            if (ifKeyInBuffer(bufferWithKey)) {
                return cleanBufferAndReturnKeyWithValue(bufferWithKey, nextKeyOrValue);
            } else {
                return putKeyIntoBufferAndReturnSeparator(bufferWithKey, nextKeyOrValue);
            }
        };
    }

    private boolean ifKeyInBuffer(List<String> bufferWithKey) {
        return bufferWithKey.size() == 1;
    }

    private List<String> cleanBufferAndReturnKeyWithValue(List<String> bufferWithKey, String nextKeyOrValue) {
        bufferWithKey.add(nextKeyOrValue);
        List<String> keyValPair = new ArrayList<>(bufferWithKey);
        bufferWithKey.clear();
        return keyValPair;
    }

    private List<String> putKeyIntoBufferAndReturnSeparator(List<String> bufferWithKey, String nextKeyOrValue) {
        bufferWithKey.add(nextKeyOrValue);
        return new ArrayList<>();
    }

    private Predicate<List<String>> removeSeparatorsBetweenGroupedPairs() {
        return l -> !l.isEmpty();
    }

    private Function<List<String>, String> getKey() {
        final int KEY_INDEX = 0;
        return kvp -> kvp.get(KEY_INDEX);
    }

    private Function<List<String>, List<String>> getValueAndWrapIntoList() {
        final int VALUE_INDEX = 1;
        return kvp -> Collections.singletonList(kvp.get(VALUE_INDEX));
    }

    private BinaryOperator<List<String>> mergeValuesForTheSameKey() {
        return (v1, v2) -> Stream.of(v1, v2).flatMap(List::stream).collect(Collectors.toList());
    }
}
