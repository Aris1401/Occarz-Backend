package com.occarz.end.security.payload.response;

import com.occarz.end.dto.response.RestResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class JwtResponse extends RestResponse {
    @Getter
    @Setter
    String token;

    @Getter
    @Setter
    String refreshToken;

    String type = "Bearer";
}
