package edu.innotech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessageInternal> internalServerError(Exception exception) {
        System.out.println("(!) INTERNAL_SERVER_ERROR");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageInternal("500", exception.getMessage(), Arrays.toString(exception.getStackTrace())));
    }

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> noDataFoundException(NoDataFoundException exception) {
        System.out.println("(!) NOT_FOUND");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> badRequestException(BadRequestException exception) {
        System.out.println("(!) BAD_REQUEST");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> mismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
