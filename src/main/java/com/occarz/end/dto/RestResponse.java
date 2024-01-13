package com.occarz.end.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RestResponse<T> {
    Date dateResponse = new Date();
    T data;
    String errorMessage = null;

    public RestResponse(T newData) {
        setData(newData);
    }
}
