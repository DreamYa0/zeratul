package com.zeratul.exception;

/**
 * Created by dreamyao on 2017/6/21.
 */
public class QueryDataException extends RuntimeException{

    private String message;

    public QueryDataException(String message) {
        this.message = message;
    }

    public QueryDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryDataException(Throwable cause) {
        super(cause);
    }

    protected QueryDataException(String message, Throwable cause,
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
