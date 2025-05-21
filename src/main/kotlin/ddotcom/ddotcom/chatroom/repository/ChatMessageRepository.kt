package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatMessage

interface ChatMessageRepository {
    fun create(chatMessage: ChatMessage): ChatMessage
    fun findById(id: String): ChatMessage?
    fun findAll(): List<ChatMessage>
    fun findByRoomId(roomId: String): List<ChatMessage>
    fun update(chatMessage: ChatMessage): ChatMessage
    fun deleteById(id: String)
}