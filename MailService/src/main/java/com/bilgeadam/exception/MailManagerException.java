package com.bilgeadam.exception;

import lombok.Getter;

@Getter
public class MailManagerException extends RuntimeException {
    private final ErrorType errorType;
    public MailManagerException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType=errorType;
    }
    public MailManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
