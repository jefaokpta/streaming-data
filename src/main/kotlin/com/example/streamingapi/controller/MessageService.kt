package com.example.streamingapi.controller

import com.example.streamingapi.repository.MessageRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.util.concurrent.Executors

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun findAll() = messageRepository.list80000()

    fun sse(): SseEmitter {
        val emitter = SseEmitter()
        println("sse")
        Executors.newSingleThreadExecutor().execute {
            try {
                messageRepository.findTop80000ByOrderByDatetimeDesc()
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

    fun streamingResponseBody(): StreamingResponseBody {
        println("streamingResponseBody")
        return StreamingResponseBody { outputStream ->
                messageRepository.findTop80000ByOrderByDatetimeDesc()
                    .forEach {
                        outputStream.write(jacksonObjectMapper().writeValueAsString(it).toByteArray())
                    }
        }
    }

}