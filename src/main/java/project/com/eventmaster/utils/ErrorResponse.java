package project.com.eventmaster.utils;

import retrofit2.HttpException;

public class ErrorResponse {

    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    static public ErrorResponse get(Throwable e) {
        HttpException exception = (HttpException) e;

        String errorMessage = exception.response().errorBody().toString();

        return new ErrorResponse(errorMessage);
    }
}
