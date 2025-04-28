package ddotcom.ddotcom.entity

import ddotcom.ddotcom.common.status.ROLE
import ddotcom.ddotcom.dto.MemberDtoResponse
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

@Document(collection = "member_info")
data class Member(
    @MongoId(FieldType.OBJECT_ID)
    var _id: String? = null,  // MongoDB 자체 ID

    @Indexed(unique = true)
    @Field("loginId")
    var loginId: String,      // 로그인용 ID

    @Field("password")
    var password: String,

    @Field("name")
    val name: String,

    @Field("phoneNumber")
    val phoneNumber: String,

    @Field("email")
    val email: String,

    @Field("university")
    val university: String,

    @Field("dormitory")
    val dormitory: String,

    @Field("role")
    val role: ROLE,

    @Field("nickname")
    var nickname: String
){
    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(loginId, name, phoneNumber, email, university, dormitory, nickname)
}