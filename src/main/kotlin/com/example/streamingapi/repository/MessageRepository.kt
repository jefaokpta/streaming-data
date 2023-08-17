package com.example.streamingapi.repository

import com.example.streamingapi.model.Message
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.util.stream.Stream

interface MessageRepository: CrudRepository<Message, String>{

    @Query("select * from whats_chats")
    fun findAllStream(): Stream<Message>
}