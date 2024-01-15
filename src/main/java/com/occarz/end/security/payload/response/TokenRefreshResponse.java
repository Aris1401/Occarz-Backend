package com.occarz.end.security.payload.response;

import com.occarz.end.dto.response.RestResponse;
import lombok.Data;

@Data
public class TokenRefreshResponse extends RestResponse<String> {
    String accessToken;
    String refreshToken;
    String tokenType = "Bearer";
}
