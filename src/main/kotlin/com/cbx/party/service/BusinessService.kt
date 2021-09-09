package com.cbx.party.service

import com.cbx.party.model.Command
import com.cbx.party.model.OrderCommand
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class BusinessService {
    @EventListener
    @Order(2)
    fun commandHandler(command: Command){
        var t = "hey"
    }
}
