package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatRoom

interface ChatRoomRepository {
    fun save(chatRoom: ChatRoom): ChatRoom
    fun findById(id: String): ChatRoom?
    fun delete(chatRoom: ChatRoom)
    fun findByParticipantIdsContaining(memberId: String): List<ChatRoom>
}