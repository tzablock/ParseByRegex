package com.parsebyregex.service.datavalidation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//TODO reason is not visible in response https://bz.apache.org/bugzilla/show_bug.cgi?id=60362
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Input data failed on validation: Input is not in form of key value pairs.")
public class NoKeyValuePairs extends RuntimeException {
}