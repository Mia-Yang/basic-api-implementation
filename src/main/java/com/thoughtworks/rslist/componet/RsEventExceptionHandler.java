package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RsEventExceptionHandler {
    @ExceptionHandler({RsEventNotValidException.class, MethodArgumentNotValidException.class, IndexOutOfBoundsException.class, IllegalArgumentException.class})
    public ResponseEntity rsEventExceptionHandler(Exception e) {
        String errorMessage;
        if(e instanceof MethodArgumentNotValidException) {
            errorMessage = "invalid param";
        } else if(e instanceof IndexOutOfBoundsException || e instanceof IllegalArgumentException) {
            errorMessage = "invalid request param";
        } else {
            errorMessage = e.getMessage();
        }
        Error error = new Error();
        error.setError(errorMessage);

        return ResponseEntity.badRequest().body(error);
    }
}
