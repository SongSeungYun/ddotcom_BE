package ddotcom.ddotcom.member.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "universities")
data class University(
    @Id
    val id: String? = null,
    val emailDomain: String,
    val name: String,
    val dormitories: List<String> // 기숙사 이름 리스트
)
