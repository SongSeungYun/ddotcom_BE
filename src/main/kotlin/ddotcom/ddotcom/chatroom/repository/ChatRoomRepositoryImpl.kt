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

    override fun save(chatRoom: ChatRoom): ChatRoom {
        return mongoTemplate.save(chatRoom)
    }

    override fun findById(id: String): ChatRoom? {
        return mongoTemplate.findById(id, ChatRoom::class.java)
    }

    override fun delete(chatRoom: ChatRoom) {
        mongoTemplate.remove(chatRoom)
    }

    override fun findByParticipantIdsContaining(memberId: String): List<ChatRoom> {
        val query = Query(Criteria.where("participantIds").`in`(memberId))
        return mongoTemplate.find(query, ChatRoom::class.java)
    }
}
