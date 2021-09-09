package com.cbx.party.utlis

import com.cbx.party.model.Command
import com.cbx.party.model.MetadataDto
import java.util.*

fun Command.toMetadataDto() = MetadataDto(
    senderId = this.senderId.toLong(),
    receiverIds = listOf(this.receiverId.toLong()),
    dialectId = this.dialect.toLong(),
    eventType = this.eventType,
    action = this.action,
    trackingId = UUID.randomUUID().toString()
)

fun Command.toMetadataDtoJson() {

}
