package com.realmart.authservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TokenDetails {
    private String tokenType;
    private Integer expiresIn;
    private String accessToken;
}
