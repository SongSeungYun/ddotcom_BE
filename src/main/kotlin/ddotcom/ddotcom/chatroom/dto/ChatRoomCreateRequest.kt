package ddotcom.ddotcom.chatroom.dto

import ddotcom.ddotcom.chatroom.entity.ChatRoomStatus

data class ChatRoomCreateRequest(
    val status: ChatRoomStatus,
    val leaderId: String,
    val participantIds: List<String>
)