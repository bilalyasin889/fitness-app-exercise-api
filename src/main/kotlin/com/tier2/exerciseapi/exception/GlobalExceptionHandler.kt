package com.tier2.exerciseapi.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseBody
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND.value(), ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGenericException(ex: Exception): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError)
    }
}
