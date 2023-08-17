package com.example.streamingapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.Date

@Table("whats_chats")
data class Message(
    @Id
    val messageId: String,
    val text: String,
    val datetime: Date,
) {
}