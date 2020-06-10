package com.parsebyregex.service.datavalidation;

import com.parsebyregex.service.datavalidation.exception.EmptyInputException;
import com.parsebyregex.service.datavalidation.exception.NoKeyValuePairs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DataValidatorServiceTest {
    private DataValidatorService out;

    @BeforeEach
    public void initObjectUnderTest() {
        out = new DataValidatorService(new LoggingService(), new ResponseService());
    }

    @Test
    void validateIfNotEmptyInputShouldReturnEmptyInputExceptionAndLogFailedValidation() {
        try {
            final String emptyInput = "";
            out.validateIfNotEmptyInput(emptyInput);
        } catch (RuntimeException e) {
            assertThat(e).isInstanceOf(EmptyInputException.class);
            assertThat(out.getSuccessLogs()).isEqualTo("");
            assertThat(out.getFailedLogs()).isEqualTo("INPUT_EMPTY: failed.");
        }
    }

    @Test
    void validateIfNotEmptyInputShouldLogSuccessValidation() {
        final String emptyInput = "something";
        out.validateIfNotEmptyInput(emptyInput);

        assertThat(out.getSuccessLogs()).isEqualTo("INPUT_EMPTY: succeeded.");
        assertThat(out.getFailedLogs()).isEqualTo("");
    }

    @Test
    void validateIfInputCreateKeyValuePairsShouldReturnNoKeyValuePairsAndLogFailedValidation() {
        try {
            final List<String> notCorrectKeyValuePair = Arrays.asList("key", "val", "key1");
            out.validateIfInputCreateKeyValuePairs(notCorrectKeyValuePair);
        } catch (RuntimeException e) {
            assertThat(e).isInstanceOf(NoKeyValuePairs.class);
            assertThat(out.getSuccessLogs()).isEqualTo("");
            assertThat(out.getFailedLogs()).isEqualTo("KEY_VALUE_PAIRS: failed.");
        }
    }

    @Test
    void validateIfInputCreateKeyValuePairsShouldLogSuccessValidation() {
        final List<String> keyValuePair = Arrays.asList("key", "val");
        out.validateIfInputCreateKeyValuePairs(keyValuePair);

        assertThat(out.getSuccessLogs()).isEqualTo("KEY_VALUE_PAIRS: succeeded.");
        assertThat(out.getFailedLogs()).isEqualTo("");
    }

    @Test
    void validateForExceptionShouldRunCodeAndForExceptionLogItAndReturnResponseStatusException() {
        try {
            out.validateForException(() -> {
                throw new RuntimeException("Some Exeption");
            });
        } catch (RuntimeException e) {
            assertThat(e).isInstanceOf(ResponseStatusException.class);
            assertThat(out.getFailedLogs()).isEqualTo("EXCEPTION_VALIDATION: failed.");
            assertThat(out.getSuccessLogs()).isEqualTo("");
        }
    }

    @Test
    void validateForExceptionShouldRunCodeAndForSuccessNotLogAnything() {
        out.validateForException(() -> {
            System.out.println("nothing");
        });
        assertThat(out.getFailedLogs()).isEqualTo("");
        assertThat(out.getFailedLogs()).isEqualTo("");
    }
}