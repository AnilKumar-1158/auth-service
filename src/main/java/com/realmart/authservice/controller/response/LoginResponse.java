package com.realmart.authservice.controller.response;

import com.realmart.authservice.model.TokenDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class LoginResponse {
    private String message;
    private Integer status;
    private TokenDetails tokenDetails;
}
