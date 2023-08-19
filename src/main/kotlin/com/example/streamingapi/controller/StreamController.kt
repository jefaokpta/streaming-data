package com.example.streamingapi.controller

import com.example.streamingapi.model.Message
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 17/08/23
 */
@RestController
class StreamController(private val messageService: MessageService) {

    @GetMapping("/sse")
    @CrossOrigin
    fun handleSse() = messageService.sse()

    @GetMapping("/list")
    fun list(): List<Message> {
        println("listing")
        return  messageService.findAll()
            .toList()
            .stream()
            .limit(8000)
            .toList()
    }
}