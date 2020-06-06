package com.parsebyregex.service.datavalidation;

import com.parsebyregex.constants.ValidationType;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private StringBuilder failedValidations;
    private StringBuilder successfulValidations;

    public LoggingService() {
        failedValidations = new StringBuilder();
        successfulValidations = new StringBuilder();
    }

    public void appendSuccessfulValidation(ValidationType vt){
        String validationMessage = String.format("%s: succeeded.", vt);
        successfulValidations.append(validationMessage);
    }

    public void appendFailedValidation(ValidationType vt){
        String validationMessage = String.format("%s: failed.", vt);
        failedValidations.append(validationMessage);
    }

    public void appendServerSideError(Exception e){
        String errorMessage = String.format("SERVER_ERROR: Parsing data failed due to exception: %s", e.getMessage());
        failedValidations.append(errorMessage);
    }

    public String getFailedLogs(){
        return failedValidations.toString();
    }

    public String getSuccessLogs(){
        return successfulValidations.toString();
    }
}
