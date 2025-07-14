package ddotcom.ddotcom.chatroom.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.Instant

@Document(collection = "chatroom_message")
data class ChatMessage(
    @MongoId(FieldType.OBJECT_ID)
    val id: String? = null,                // 메시지 고유번호 (MongoDB ObjectId)
    val roomId: String,                    // 채팅방 고유번호
    val senderId: String,                  // 보낸사람 고유번호
    val sentAt: Instant = Instant.now(),   // 보낸시간
    val content: String                    // 내용
)