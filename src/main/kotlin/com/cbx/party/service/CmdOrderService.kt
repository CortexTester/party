package com.cbx.party.service

import com.cbx.party.model.Command
import com.cbx.party.model.OrderCommand
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class CmdOrderService {
    @EventListener
    @Order(1)
    fun commandHandler(command: Command){
        val t = "can you see me"
    }
}
