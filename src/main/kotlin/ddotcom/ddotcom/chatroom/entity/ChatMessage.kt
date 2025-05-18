package ddotcom.ddotcom.chatroom.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "chatroom_message")
data class ChatMessage(
    @Id
    val id: String? = null,                // 메시지 고유번호 (MongoDB ObjectId)
    val roomId: String,                    // 채팅방 고유번호
    val senderId: String,                  // 보낸사람 고유번호
    val sentAt: Instant = Instant.now(),   // 보낸시간
    val content: String                    // 내용
)