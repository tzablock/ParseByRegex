package com.parsebyregex.service.datavalidation;

import com.parsebyregex.constants.ValidationType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {//TODO replace responses with response Exceptions  https://www.baeldung.com/spring-response-status-exception
    private ResponseEntity<String> response;

    public void failedResponseByValidation(ValidationType failedValidation) {
        response = new ResponseEntity<>(String.format("Input data failed on validation: %s", failedValidation), HttpStatus.BAD_REQUEST);
    }

    public void failedResponseByInternalError(Exception e) {
        response = new ResponseEntity<>(String.format("Input data failed due to internal exception: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> getResponse(){
        return response == null ? new ResponseEntity<String>("Data parsed successfully.", HttpStatus.OK) : response;
    }
}
