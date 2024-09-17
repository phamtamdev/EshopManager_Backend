package com.tambao.EshopManeger_Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class OAuthDto {
    private Integer id;
    private String fullName;
    private String userName;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String source;
    private String avatar;
    private LocalDate creationDate;
}
