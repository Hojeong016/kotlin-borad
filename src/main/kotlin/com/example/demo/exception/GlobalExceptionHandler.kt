package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException::class)
    fun nullpointerExceptionHandler(er : NullPointerException) : ResponseEntity<String> = ResponseEntity<String>("null값이 들어왔습니다.",HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun AllException(err: Exception) : ResponseEntity<String> = ResponseEntity<String>("서버 오류가 발생했습니다.",HttpStatus.INTERNAL_SERVER_ERROR)
}