package com.example.streamingapi.repository

import com.example.streamingapi.model.Message
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

interface MessageRepository: CrudRepository<Message, String>{

    @Transactional(readOnly = true)
    fun findTop80000ByOrderByDatetimeDesc(): Stream<Message>

    @Query("select * from whats_chats order by datetime desc limit 8000")
    fun list80000(): List<Message>


}