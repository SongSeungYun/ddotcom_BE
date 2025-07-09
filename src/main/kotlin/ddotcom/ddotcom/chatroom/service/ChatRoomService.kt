package ddotcom.ddotcom.chatroom.service

import ddotcom.ddotcom.chatroom.entity.ChatRoom
import ddotcom.ddotcom.chatroom.entity.ChatRoomStatus
import ddotcom.ddotcom.chatroom.repository.ChatRoomRepository
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {

    fun createRoom(name: String): ChatRoom {
        val room = ChatRoom(
            id = null,
            productId = "aaaa",
            status = ChatRoomStatus.PUBLIC,
            leaderId = "leaderID",
            participantIds = {"part1", "part2"}
        )
        return chatRoomRepository.save(room)
    }

    fun findAllRooms(): List<ChatRoom> =
        chatRoomRepository.findAll()

    fun findRoomById(roomId: String): ChatRoom? =
        chatRoomRepository.findById(roomId).orElse(null)

    fun deleteRoom(roomId: String) {
        chatRoomRepository.deleteById(roomId)
    }
}