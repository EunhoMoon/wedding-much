package me.janek.weddingmuch.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hello")
class HelloController {

  @GetMapping
  fun getHello(): ResponseEntity<String> = ResponseEntity.status(HttpStatus.OK).body("Hello!")

}