//package ddotcom.ddotcom.member.repository
//
//import ddotcom.ddotcom.entity.Member
//import org.springframework.data.mongodb.repository.MongoRepository
//
//interface MemberRepository : MongoRepository<Member, String> {
//    fun findByLoginId(loginId: String): Member? //id중복검사를 위해 해놨음
//}