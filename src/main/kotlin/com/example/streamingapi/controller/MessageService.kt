package com.example.streamingapi.controller

import com.example.streamingapi.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun findAll() = messageRepository.findAll()

    fun findAllStream() = messageRepository.findAllStream()

}