package com.example.streamingapi.repository

import com.example.streamingapi.model.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

interface MessageRepository: CrudRepository<Message, String>{

    @Transactional(readOnly = true)
    fun findTop8000ByOrderByDatetimeDesc(): Stream<Message>
}