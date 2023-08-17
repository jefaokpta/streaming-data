package com.example.streamingapi.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.stream.Stream


/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 17/08/23
 */
@Controller
class StreamController {

    @GetMapping("/sse")
    fun handleSse(): SseEmitter {
        val emitter = SseEmitter()
        try {
            println("emitting")
            CompletableFuture.runAsync {
                createList()
                    .forEach {
                        emitter.send(it.toString() + "-SSE", MediaType.TEXT_PLAIN)
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
}