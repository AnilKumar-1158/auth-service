package com.realmart.authservice.model;


import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private String httpMethod;
    private Integer status;
    private String error;
    private String path;
    private Instant timestamp;
}
