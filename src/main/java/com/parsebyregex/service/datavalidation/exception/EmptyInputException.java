package com.parsebyregex.service.datavalidation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Input data failed on validation: Input is an empty String.")
public class EmptyInputException extends RuntimeException{
}
