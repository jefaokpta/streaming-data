package com.example.streamingapi.controller

import com.example.streamingapi.model.Message
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.stream.Stream


/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 17/08/23
 */
@RestController
class StreamController(private val messageService: MessageService) {

    @GetMapping("/sse")
    fun handleSse(): SseEmitter {
        val emitter = SseEmitter(TimeUnit.MINUTES.toMillis(1))
        try {
            println("emitting sse")
            CompletableFuture.runAsync {
                messageService.findAllStream()
                    .limit(8000)
                    .forEach {
                        emitter.send("${it.datetime} - ${it.text}", MediaType.TEXT_PLAIN)
                    }
                emitter.send("CLOSE", MediaType.TEXT_PLAIN)
                emitter.complete()
            }
        } catch (ex: Exception) {
            emitter.completeWithError(ex)
        }
        return emitter
    }

    private fun createList(): Stream<Int> = Stream.iterate(0) {
        TimeUnit.SECONDS.sleep(1)
        it + 1
    }.limit(10)

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