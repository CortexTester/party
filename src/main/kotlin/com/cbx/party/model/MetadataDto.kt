package com.cbx.party.model

//event's metadata
data class MetadataDto(
    val senderId: Long, //todo: removed, should get senderId from principle
    val receiverIds: List<Long>,
    val dialectId: Long, //may be add doc type and action. default document name is main.json
    val eventType: String,
    val action: String,
    val trackingId: String,
    val contentFileName: String? = "main.json"
)
