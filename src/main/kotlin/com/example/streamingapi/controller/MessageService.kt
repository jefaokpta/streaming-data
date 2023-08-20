package com.example.streamingapi.controller

import com.example.streamingapi.repository.MessageRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.Executors

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun findAll() = messageRepository.findAll()

    fun sse(): SseEmitter {
        val emitter = SseEmitter()
        println("sse")
        Executors.newSingleThreadExecutor().execute {
            try {
                messageRepository.findTop8000ByOrderByDatetimeDesc()
                    .forEach {
                        emitter.send(
                            SseEmitter.event()
                            .data(jacksonObjectMapper().writeValueAsString(it))
                            .id(it.messageId)
                            .name("message")
                        )
                    }
                emitter.send(SseEmitter.event()
                    .name("close")
                    .data("close")
                )
                emitter.complete()
            } catch (ex: Exception) {
                emitter.completeWithError(ex)
            }
        }
        return emitter
    }

}