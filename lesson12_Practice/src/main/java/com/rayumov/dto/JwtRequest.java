package com.rayumov.dto;

import lombok.Data;


// Когда пользователь хочет прислать на сервер логин и пароль в обмен на токе.
@Data
public class JwtRequest {
    private String username;
    private String password;
}
