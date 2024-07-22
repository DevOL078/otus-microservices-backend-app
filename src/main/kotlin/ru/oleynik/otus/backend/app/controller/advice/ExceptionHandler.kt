package ru.oleynik.otus.backend.app.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(e: Exception): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(e.message)
    }

}