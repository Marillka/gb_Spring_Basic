package com.rayumov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Возвращаем клиенту токен в ответ на логин и пароль.
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
