package com.rayumov.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Глобальный контроллер перехватки исключений.
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler - говорим, что хотим перехватить исключение, которое где то может вылетить.
    // Конкретно перехватываем ResourceNotFoundException или его наследников.
    // Если прилает такое исключение, то мы его пакуем в ResponseEntity<AppError> и возвращаем клиенту.
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
