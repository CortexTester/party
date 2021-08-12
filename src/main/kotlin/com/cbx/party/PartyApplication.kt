package com.cbx.party

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableProcessApplication
class PartyApplication

fun main(args: Array<String>) {
    runApplication<PartyApplication>(*args)
}
