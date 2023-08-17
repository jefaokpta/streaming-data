package com.example.streamingapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 17/08/23
 */
@Controller
class StreamController {

    @GetMapping("/stream")
    fun listStream(): ResponseEntity<StreamingResponseBody>{
        println("streaming")
        val numberList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val responseBody = StreamingResponseBody { outputStream ->
            val writer = outputStream.bufferedWriter()
            numberList.forEach {
                writer.write(it.toString() + "Stream")
//                writer.newLine()
                Thread.sleep(1000)
            }
            writer.flush()
        }
        return ResponseEntity.ok(responseBody)
    }
}