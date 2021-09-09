package com.cbx.party.model

import cbx.ubl.Order
import org.springframework.web.multipart.MultipartFile

interface Command {
    var senderId: String
    var receiverId: String //one to one for now. it may be multiple receivers
    var dialect: String
    var eventType: String
    var action: String
}

data class OrderCommand (
    override var senderId: String,
    override var receiverId: String, //one to one for now. it may be multiple receivers
    override var dialect: String,
    override var eventType: String,
    override var action: String,
    var order: Order,
    var attachments: MutableList<MultipartFile>? = mutableListOf()
) : Command
