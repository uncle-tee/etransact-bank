package com.etransact.accountmanagment.data;

import lombok.Data;

@Data
public class ResponseData<T> {
    private T data;
    private String message;

    public ResponseData(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseData(String message) {
        this.message = message;
    }
}
