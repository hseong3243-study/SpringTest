package com.example.restdocs;

public class ApiResult<T> {
    private final T data;

    public ApiResult(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
