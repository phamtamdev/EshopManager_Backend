package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private Boolean enabled;
    private String source;
    private String avatar;
    private Integer sex;
    private LocalDate creationDate;
    private List<RoleDto> roles;
}
