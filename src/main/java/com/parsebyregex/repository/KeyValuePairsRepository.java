package com.parsebyregex.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class KeyValuePairsRepository {//getting variables from properties: https://www.baeldung.com/properties-with-spring
    @Value("${repository.path}")//TODO make it dynamic for environments local,dev,uat,prod
    private String pathToLocalRepository;

    public void saveOrUpdate(Map<String, List<String>> keyValuePars){
        keyValuePars.forEach((key, values) -> {
            values.sort(Comparator.naturalOrder());
            if (ifToSave(key)){
                saveKeyValues(key, values);
            } else {
                updateKeyValues(key, values);
            }
        });
    }

    private void saveKeyValues(String key, List<String> values) {
        try {
            Files.write(Paths.get(pathToLocalRepository + "/" + key), String.join("\n", values).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateKeyValues(String key, List<String> values) {
        try {
            Path fullFilePath = Paths.get(pathToLocalRepository + "/" + key);
            List<String> lines = Files.readAllLines(fullFilePath);
            lines.addAll(values);
            List<String> distinctValues = lines.stream().distinct().collect(Collectors.toList());
            Files.write(fullFilePath, String.join("\n", distinctValues).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ifToSave(String key) {
        return Files.notExists(Paths.get(pathToLocalRepository + "/" + key));
    }
}
