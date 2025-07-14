package ddotcom.ddotcom.chatroom.service

import ddotcom.ddotcom.chatroom.dto.ChatMessageSendRequest
import ddotcom.ddotcom.chatroom.entity.ChatMessage
import ddotcom.ddotcom.chatroom.repository.ChatMessageRepository
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository
) {
    fun saveMessage(request: ChatMessageSendRequest): ChatMessage {
        val chatMessage = ChatMessage(
            roomId = request.roomId,
            senderId = request.senderId,
            content = request.content
        )
        return chatMessageRepository.create(chatMessage)
    }
}