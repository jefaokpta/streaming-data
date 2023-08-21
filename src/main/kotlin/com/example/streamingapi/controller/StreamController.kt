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

    @CrossOrigin
    @GetMapping("/sse")
    fun handleSse() = messageService.sse()

    @CrossOrigin
    @GetMapping("/list")
    fun list(): List<Message> {
        println("listing")
        return  messageService.findAll()
    }

    @CrossOrigin
    @GetMapping("/stream")
    fun handleStream() = messageService.streamingResponseBody()
}