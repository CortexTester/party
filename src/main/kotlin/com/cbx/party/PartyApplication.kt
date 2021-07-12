package com.cbx.party

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PartyApplication

fun main(args: Array<String>) {
    runApplication<PartyApplication>(*args)
}
