package com.cbx.party

import com.cbx.party.config.CbxProperties
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableProcessApplication
@EnableConfigurationProperties(CbxProperties::class)
@EnableAsync
class PartyApplication

fun main(args: Array<String>) {
    runApplication<PartyApplication>(*args)
}
