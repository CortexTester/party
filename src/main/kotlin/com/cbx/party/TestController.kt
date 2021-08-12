package com.cbx.party

import cbx.ubl.Order
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngines
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(
    val repositoryService: RepositoryService,
    val taskService: TaskService,
    val runtimeService: RuntimeService
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

    @PostMapping("/ubl/")
    fun ubl(@RequestBody order:Order): String {

        return "ok"
    }

}
