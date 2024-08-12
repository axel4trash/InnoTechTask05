package edu.innotech.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessageInternal{
    private String code;
    private String message;
    private String stackTrace;
}
