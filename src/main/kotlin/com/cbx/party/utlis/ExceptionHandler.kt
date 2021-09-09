package com.cbx.party.utlis

import java.time.LocalDateTime
import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ContentInvalidException::class)
    fun contentInvalidException(ex: ContentInvalidException, request: WebRequest): ResponseEntity<ErrorMessage?>? {
        val message = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            ex.message!!,
            request.getDescription(false)
        )
        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CommandHandleException::class)
    fun commandHandleExceptionException(
        ex: CommandHandleException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage?>? {
        val message = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            ex.message!!,
            request.getDescription(false)
        )
        return ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorMessage(
    var statusCode: Int,
    var data: LocalDateTime,
    var message: String,
    var description: String?
)

class ContentInvalidException(message: String) : RuntimeException(message)

class CommandHandleException(message: String) : RuntimeException(message)
