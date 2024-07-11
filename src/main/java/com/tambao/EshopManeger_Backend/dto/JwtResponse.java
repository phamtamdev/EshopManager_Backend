package com.tambao.EshopManeger_Backend.dto;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private int statusCode;
    private String token;
    private String message;
    private String error;
    private String expirationTime;
    private UserDto UserDto;
    private List<String> roles;
}
