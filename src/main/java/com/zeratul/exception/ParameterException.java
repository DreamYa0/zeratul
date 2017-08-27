package com.zeratul.exception;

/**
 * Created by dreamyao on 2017/6/12.
 */
public class ParameterException extends RuntimeException {

    private String message;

    public ParameterException(String message) {
        this.message = message;
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    protected ParameterException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
