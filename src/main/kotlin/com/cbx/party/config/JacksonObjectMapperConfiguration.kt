package com.cbx.party.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class JacksonObjectMapperConfiguration {
    companion object {
        private const val dateFormat = "yyyy-MM-dd"
        private const val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
    }

//    @Bean fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
//        return Jackson2ObjectMapperBuilderCustomizer {
//                builder -> builder.simpleDateFormat(dateFormat)
//            builder.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
//            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
//            builder.deserializers(LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
//            builder.deserializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
//        }
//    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule().addDeserializer(
                LocalDateTime::class.java, LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ))
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

}
