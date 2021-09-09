package com.cbx.party.service

import com.cbx.party.config.CbxProperties
import com.cbx.party.model.Command
import com.cbx.party.model.EventResponseDto
import com.cbx.party.model.OrderCommand
import com.cbx.party.utlis.CommandHandleException
import com.cbx.party.utlis.logger
import com.cbx.party.utlis.toMetadataDto
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
//import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile


@Component
class HubService(val cbxProperties: CbxProperties, val okHttpClient: OkHttpClient, val objectMapper: ObjectMapper) {

    @EventListener
    @Order(0)
    fun commandHandler(command: Command) {
        when (command) {
            is OrderCommand -> sendToHub(command)
            else -> throw CommandHandleException("HubService --- command failed to lookup")
        }
    }

//    private fun sendToHubTest(command: OrderCommand){
//        val request = Request.Builder()
//            .url(cbxProperties.hubBaseUrl + "/test/")
//            .build()
//        val client = OkHttpClient()
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//            for ((name, value) in response.headers) {
//                println("$name: $value")
//            }
//
//            println(response.body!!.string())
//        }
//    }

    private fun sendToHub(command: OrderCommand) {
        val metadataDtoJson: String = getMetadataDtoJson(command)
        val orderJson: String = getOrderJson(command)
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "metadata",
                "metadata.json",
                metadataDtoJson.toRequestBody(MediaType.APPLICATION_JSON_VALUE.toMediaType())
            )
            .addFormDataPart(
                "files",
                "main.json",
                orderJson.toRequestBody(MediaType.APPLICATION_JSON_VALUE.toMediaType())
            )
        command.attachments?.forEach { multipartFile: MultipartFile ->
            requestBody.addFormDataPart(
                name = "files",
                filename = multipartFile.originalFilename,
                multipartFile.bytes.toRequestBody(multipartFile.contentType?.toMediaType())
            )
        }

        val request = Request.Builder()
            .url(cbxProperties.hubBaseUrl + "/event")
            .post(requestBody.build())
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw CommandHandleException("sendToHub --- failed: $response")

            val eventResponse = objectMapper.readValue(response.body!!.string(), EventResponseDto::class.java)
            val test = eventResponse
            //todo: invoke repository to save command
        }
    }

//    fun getFile(filename: String, extentName: String, content: String): File {
//        val testFile: Path = Files.createTempFile(filename, extentName)
//        logger().debug("Creating and Uploading Test File: $testFile")
//        Files.write(testFile, content.toByteArray())
//        return testFile.toFile()
//    }

    fun getMetadataDtoJson(command: Command): String = objectMapper.writeValueAsString(command.toMetadataDto())

    fun getOrderJson(command: OrderCommand): String = objectMapper.writeValueAsString(command.order)
}
