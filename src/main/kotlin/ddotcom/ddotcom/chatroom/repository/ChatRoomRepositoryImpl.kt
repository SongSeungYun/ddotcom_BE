package ddotcom.ddotcom.chatroom.repository

import ddotcom.ddotcom.chatroom.entity.ChatRoom
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepositoryImpl(
    @Qualifier("chatMongoTemplate") private val mongoTemplate: MongoTemplate
) : ChatRoomRepository {

    private val collectionName = "chatroom_info"

    override fun save(chatRoom: ChatRoom): ChatRoom {
        return mongoTemplate.save(chatRoom, collectionName)
    }

    override fun findById(id: String): ChatRoom? {
        return mongoTemplate.findById(id, ChatRoom::class.java, collectionName)
    }

    override fun findByProductId(productId: String): ChatRoom? {
        val query = Query(Criteria.where("productId").`is`(productId))
        return mongoTemplate.findOne(query, ChatRoom::class.java, collectionName)
    }

    override fun delete(chatRoom: ChatRoom) {
        val query = Query(Criteria.where("_id").`is`(chatRoom.id))
        mongoTemplate.remove(query, collectionName)
    }

    override fun findByParticipantIdsContaining(memberId: String): List<ChatRoom> {
        val query = Query(Criteria.where("participantIds").`in`(memberId))
        return mongoTemplate.find(query, ChatRoom::class.java, collectionName)
    }
}
