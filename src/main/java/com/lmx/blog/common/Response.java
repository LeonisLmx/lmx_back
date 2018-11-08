package com.lmx.blog.common;

import org.springframework.http.HttpStatus;

public class Response<T> {
    private T body;
    private String message;
    private HttpStatus httpStatus;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static Response ok(Object object){
        Response response = new Response();
        response.setBody(object);
        response.setMessage("执行成功");
        response.setHttpStatus(HttpStatus.OK);
        return response;
    }
}
