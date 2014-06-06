package com.restfiddle.exceptions;

/**
 * Created by santoshm1 on 06/06/14.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorMessage handleException(ApiException ex) {
	ErrorMessage errorMessage = new ErrorMessage(ex);
	return errorMessage;
    }

}
