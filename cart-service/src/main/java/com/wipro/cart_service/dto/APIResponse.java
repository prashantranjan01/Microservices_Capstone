package com.wipro.cart_service.dto;

import org.springframework.http.HttpStatus;

public class APIResponse<T> {

    boolean status = true;
    int statusCode;
    String info;
    T data;

    public APIResponse(boolean status, HttpStatus statusCode, String info, T data) {
        this.status = status;
        this.statusCode = statusCode.value();
        this.info = info;
        this.data = data;
    }

    public APIResponse(HttpStatus statusCode, String info, T data) {
        this.statusCode = statusCode.value();
        this.info = info;
        this.data = data;
        if (data == null) {
            this.status = false;
        }
    }

    public APIResponse(HttpStatus statusCode, T data) {
        this.statusCode = statusCode.value();
        this.data = data;
        this.info = "SUCCESS";
    }

    public APIResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
