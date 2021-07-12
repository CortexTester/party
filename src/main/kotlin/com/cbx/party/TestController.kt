package com.cbx.party

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/")
    fun test():String = "Test Camunda 01"
}
