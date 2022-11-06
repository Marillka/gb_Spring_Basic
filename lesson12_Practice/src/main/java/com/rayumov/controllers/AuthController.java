package com.rayumov.controllers;
import com.rayumov.dto.JwtRequest;
import com.rayumov.dto.JwtResponse;
import com.rayumov.exceptions.AppError;
import com.rayumov.services.UserService;
import com.rayumov.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Контролер для получения токена по логину и паролю.
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    // AuthenticationManager - запчасть от SpringSecurity, он умеет по логину и паролю проверять, есть ли такой пользователь.
    private final AuthenticationManager authenticationManager;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            // Сначала проводим проверку - корректный ли логин и пароль нам прислал клиент
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // Если логин или пароль неправильные - бросается exception.
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);// code: 401
        }

        // если логин и пароль проверку прошли - то получаем по ним UserDetails (минимальную информацию о пользователе).
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        // Формируем токен.
        String token = jwtTokenUtil.generateToken(userDetails);
        // в ответ клиенту отправляем токен.
        return ResponseEntity.ok(new JwtResponse(token));
    }


}