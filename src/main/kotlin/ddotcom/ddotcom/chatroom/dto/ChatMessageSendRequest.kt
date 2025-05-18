package ddotcom.ddotcom.chatroom.dto

data class ChatMessageSendRequest(
    val roomId: String,
    val senderId: String,
    val content: String
)