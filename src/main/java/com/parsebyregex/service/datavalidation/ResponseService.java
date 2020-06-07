package com.parsebyregex.service.datavalidation;

import com.parsebyregex.constants.ValidationType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResponseService {//https://www.baeldung.com/spring-response-status-exception
    public void failedResponseByValidation(RuntimeException exception) {
        throw exception;
    }

    public void failedResponseByInternalError(Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                          String.format("Input data failed due to internal exception: %s", e.getMessage()),
                                          e);
    }

    public ResponseEntity<String> getResponse(){
        return new ResponseEntity<>("Data parsed successfully.", HttpStatus.OK);
    }
}
