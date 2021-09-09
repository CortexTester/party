package com.cbx.party.api

import cbx.ubl.Order
import com.cbx.party.model.OrderCommand
import com.cbx.party.utlis.ContentInvalidException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class CommandController(
    val repositoryService: RepositoryService,
    val taskService: TaskService,
    val runtimeService: RuntimeService,
    val objectMapper: ObjectMapper,
    val publisher: ApplicationEventPublisher
) {
    @GetMapping("/test/")
    fun test(): String {
//        val engines = ProcessEngines.getDefaultProcessEngine()
//        val repository = engines.repositoryService
        val id = "TestBpmn01"

//        val processes = repositoryService.createProcessDefinitionQuery()
//            .processDefinitionKey(id)
//            .orderByProcessDefinitionVersion()
//            .asc()
//            .list()
//
//        val process = runtimeService.startProcessInstanceByKey(id, mapOf("trackingId" to "test01"))

        val task = taskService.createTaskQuery()
            .or().processDefinitionKey("TestBpmn01").endOr()
            .list()

        taskService.complete(task.first().id, mapOf("start-info" to "start task has done"))


        return "test cmd"

    }

//    @PostMapping("/send")
//    fun ubl(
//        @RequestParam("receiver", required = true) receiver: String,
//        @RequestParam("doctype", required = true) doctype: String,
//        @RequestParam("action", required = true) action: String,
//        @RequestBody order:Order): String {
//        val orderEvent = OrderCommand(senderId = "me", receiverId = receiver, docType = doctype, action=action, order = order)
//        publisher.publishEvent(orderEvent)
//        return "ok"
//    }

    @PostMapping("/command", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun processCommand(
        @RequestParam("receiverId", required = true) receiver: String,
        @RequestParam("eventType", required = true) doctype: String,
        @RequestParam("action", required = true) action: String,
        @RequestPart("content") orderString: String,
        @RequestPart("attachments") attachments: MutableList<MultipartFile>
    ): String {
//        val orderEvent = OrderEvent(senderId = "me", receiverId = receiver, docType = doctype, action=action, order = orderEventDto.order)
//        publisher.publishEvent(orderEvent)
        var order: Order;
        try {
            order = objectMapper.readValue(orderString);
        } catch (ex: Exception) {
            throw ContentInvalidException(ex.message ?: "failed to convert json string to order")
        }
        val orderCommand = OrderCommand(
            senderId = "1",
            receiverId = receiver,
            eventType = doctype,
            action = action,
            order = order,
            attachments = attachments,
            dialect = "1"
        )
        publisher.publishEvent(orderCommand)
        return "ok"
    }


}
