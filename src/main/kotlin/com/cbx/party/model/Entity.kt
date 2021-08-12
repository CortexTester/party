package com.cbx.party.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Event(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var senderId: String,
    var receiverId: String,
    var docType: String,
    var action: String,
    var trackingId: String, //got files from hub
    var processKey: String, //bpmn process
    var businessKey: String //bpmn instance
)
