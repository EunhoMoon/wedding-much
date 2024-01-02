package me.janek.weddingmuch

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = [MethodArgumentNotValidException::class])
  fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
    val errors = e.bindingResult.fieldErrors.associate { it.field to it.defaultMessage!! }
    return ResponseEntity.badRequest().body(errors)
  }

}