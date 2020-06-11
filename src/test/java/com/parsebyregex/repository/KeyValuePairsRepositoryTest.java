package com.parsebyregex.repository;

import com.parsebyregex.config.PropertiesInitializer;
import com.parsebyregex.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@TestPropertySource("/test-repository.properties")
@ContextConfiguration(classes = {TestConfig.class}, initializers = PropertiesInitializer.class)
class KeyValuePairsRepositoryTest {
    @Autowired
    private KeyValuePairsRepository out;
    @Value("${repository.path}")
    private String pathToLocalRepository;

    @Test
    void saveOrUpdateShouldSaveTwoValuesSeparatedWithNewLinesToFileWithKeyName() {
        String file1 = "file1";
        String file2 = "file2";
        List<String> values1 = Arrays.asList("val1", "val2");
        List<String> values2 = Arrays.asList("val3", "val4");

        Map<String, List<String>> keyValuePars = new HashMap<String, List<String>>() {{
            put(file1, values1);
            put(file2, values2);
        }};

        out.saveOrUpdate(keyValuePars);
        List<String> readValues1 = readFileContentAndRemoveFile(String.format("%s/%s", pathToLocalRepository, file1));
        List<String> readValues2 = readFileContentAndRemoveFile(String.format("%s/%s", pathToLocalRepository, file2));

        assertThat(readValues1).hasSize(2);
        assertThat(readValues2).hasSize(2);
        assertThat(readValues1).contains(values1.get(0), values1.get(1));
        assertThat(readValues2).contains(values2.get(0), values2.get(1));
    }

    private List<String> readFileContentAndRemoveFile(String fileLoc) {
        try {
            Path filePath = Paths.get(fileLoc);
            List<String> values = Files.readAllLines(filePath);
            Files.delete(filePath);
            return values;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}