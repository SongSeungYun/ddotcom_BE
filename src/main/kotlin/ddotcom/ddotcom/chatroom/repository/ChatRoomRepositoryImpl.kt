package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatRoom
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ChatRoomRepository {
    override fun create(chatRoom: ChatRoom): ChatRoom =
        mongoTemplate.insert(chatRoom)

    override fun findById(id: String): ChatRoom? =
        mongoTemplate.findById(id, ChatRoom::class.java)

    override fun findAll(): List<ChatRoom> =
        mongoTemplate.findAll(ChatRoom::class.java)

    override fun update(chatRoom: ChatRoom): ChatRoom =
        mongoTemplate.save(chatRoom)

    override fun deleteById(id: String) {
        val query = Query(Criteria.where("_id").`is`(id))
        mongoTemplate.remove(query, ChatRoom::class.java)
    }
}