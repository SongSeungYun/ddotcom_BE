package ddotcom.ddotcom.chatroom.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.Instant

@Document(collection = "chatroom_info")
data class ChatRoom(
    @MongoId(FieldType.OBJECT_ID)
    val id: String? = null,                // 채팅방 고유번호 (MongoDB ObjectId)
    val productId: String,                  // 물건 고유번호
    val createdAt: Instant = Instant.now(),// 채팅방 생성시각
    val status: ChatRoomStatus,            // 채팅방 상태 (비공개/공개)
    val leaderId: String,                  // 팀장 ID
    val participantIds: List<String>       // 참여한 사람 ID 목록
)

enum class ChatRoomStatus {
    PRIVATE, PUBLIC                        // 비공개, 공개
}