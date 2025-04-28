//package ddotcom.ddotcom.member.entity
//
////import ddotcom.ddotcom.common.status.ROLE
////import ddotcom.ddotcom.member.dto.MemberDtoResponse
//import org.springframework.data.annotation.Id
//import org.springframework.data.mongodb.core.index.Indexed
//import org.springframework.data.mongodb.core.mapping.Document
////import java.time.LocalDate
////import java.time.format.DateTimeFormatter
//
//@Document(collection = "members")
//class Member(
//    @Id
//    var id: String? = null,  // MongoDB는 String 타입의 `_id`를 기본적으로 사용
//
//    @Indexed(unique = true)
//    val loginId: String,
//    val password: String,
//    val name: String,
//    val gender: Gender,
//    val email: String,
//
//    // MongoDB에서는 @OneToMany 대신 List 사용
//    //val roles: List<ROLE> = listOf()
//)
////{
////    private fun LocalDate.formatDate(): String =
////        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
////
////    fun toDto(): MemberDtoResponse =
////        MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)
////}
