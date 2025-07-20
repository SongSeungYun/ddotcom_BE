package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatRoom

interface ChatRoomRepository {
    fun save(chatRoom: ChatRoom): ChatRoom
    fun findById(id: String): ChatRoom?
    fun findByProductId(productId: String): ChatRoom? // productId로 채팅방을 찾는 함수 추가
    fun delete(chatRoom: ChatRoom)
    fun findByParticipantIdsContaining(memberId: String): List<ChatRoom>
}
