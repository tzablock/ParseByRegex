package com.parsebyregex.service.datavalidation;

import com.parsebyregex.constants.ValidationType;
import com.parsebyregex.service.datavalidation.exception.EmptyInputException;
import com.parsebyregex.service.datavalidation.exception.NoKeyValuePairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataValidatorService {
    private LoggingService logging;
    private ResponseService response;

    @Autowired
    public DataValidatorService(LoggingService logging, ResponseService response) {
        this.logging = logging;
        this.response = response;
    }

    public void validateIfNotEmptyInput(final String strToParse) {
        if (strToParse.isEmpty()){
            logging.appendFailedValidation(ValidationType.INPUT_EMPTY);
            response.failedResponseByValidation(new EmptyInputException());
        } else {
            logging.appendSuccessfulValidation(ValidationType.INPUT_EMPTY);
        }
    }

    public void validateIfInputCreateKeyValuePairs(final List<String> keyValuePars) {
        if (isNotEven(keyValuePars)){
            logging.appendFailedValidation(ValidationType.KEY_VALUE_PAIRS);
            response.failedResponseByValidation(new NoKeyValuePairs());
        } else {
            logging.appendSuccessfulValidation(ValidationType.KEY_VALUE_PAIRS);
        }
    }

    public void validateForException(Runnable invokeParsing){
        try {
            invokeParsing.run();
        } catch (RuntimeException e){
            logging.appendFailedValidation(ValidationType.EXCEPTION_VALIDATION);
            response.failedResponseByInternalError(e);
        }
    }

    public ResponseEntity<String> getResponse(){
        return response.getResponse();
    }

    public String getFailedLogs(){
        return logging.getFailedLogs();
    }

    public String getSuccessLogs(){
        return logging.getSuccessLogs();
    }

    private boolean isNotEven(List<String> keyValuePars) {
        return keyValuePars.size()%2 != 0;
    }
}
