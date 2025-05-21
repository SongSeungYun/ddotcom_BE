package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatMessage
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ChatMessageRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ChatMessageRepository {
    override fun create(chatMessage: ChatMessage): ChatMessage =
        mongoTemplate.insert(chatMessage)

    override fun findById(id: String): ChatMessage? =
        mongoTemplate.findById(id, ChatMessage::class.java)

    override fun findAll(): List<ChatMessage> =
        mongoTemplate.findAll(ChatMessage::class.java)

    override fun findByRoomId(roomId: String): List<ChatMessage> {
        val query = Query(Criteria.where("roomId").`is`(roomId))
        return mongoTemplate.find(query, ChatMessage::class.java)
    }

    override fun update(chatMessage: ChatMessage): ChatMessage =
        mongoTemplate.save(chatMessage)

    override fun deleteById(id: String) {
        val query = Query(Criteria.where("_id").`is`(id))
        mongoTemplate.remove(query, ChatMessage::class.java)
    }
}