package project.com.eventmaster.utils;

import retrofit2.HttpException;

public class ErrorResponse {

    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    static public ErrorResponse get(Throwable e) {
        HttpException exception = (HttpException) e;
//        ((HttpException) e).response.errorBody().string()

        String errorMessage = exception.response().errorBody().toString();

        return new ErrorResponse(errorMessage);
    }


    static public ErrorResponse get(String message) {

        return new ErrorResponse(message);
    }
}
