package ddotcom.ddotcom.member.repository

import ddotcom.ddotcom.member.entity.Member
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : MemberRepository {

    override fun findMemberByLoginId(loginId: String): Member? {
        val query = Query(Criteria.where("loginId").`is`(loginId))
        return mongoTemplate.findOne(query, Member::class.java, "member_info")
    }

    override fun isLoginIdAvailable(loginId: String): Boolean {
        return try {
            val query = Query(Criteria.where("loginId").`is`(loginId))
            val member = mongoTemplate.findOne(query, Member::class.java, "member_info")
            member == null
        } catch (e: Exception) {
            println("Error checking login ID availability: $e")
            false
        }
    }

    override fun isNicknameAvailable(nickname: String): Boolean {
        return try {
            val query = Query(Criteria.where("nickname").`is`(nickname))
            val member = mongoTemplate.findOne(query, Member::class.java, "member_info")
            member == null
        } catch (e: Exception) {
            println("Error checking login ID availability: $e")
            false
        }
    }
}
