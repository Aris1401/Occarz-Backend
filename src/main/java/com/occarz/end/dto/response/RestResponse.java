package com.occarz.end.dto.response;

import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
public class RestResponse<T> {
    Date dateResponse = new Date();
    T data;
    String errorMessage = null;
    String path;
    int status = HttpServletResponse.SC_OK;
    public RestResponse() {}

    public RestResponse(T newData) {
        setData(newData);
    }
}
