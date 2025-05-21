package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatRoom

interface ChatRoomRepository {
    fun create(chatRoom: ChatRoom): ChatRoom
    fun findById(id: String): ChatRoom?
    fun findAll(): List<ChatRoom>
    fun update(chatRoom: ChatRoom): ChatRoom
    fun deleteById(id: String)
}