package com.socialmeda.exception;

import lombok.Getter;

@Getter
public class PostServiceException extends RuntimeException{
    private final ErrorType errorType;
    public PostServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
    public PostServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
