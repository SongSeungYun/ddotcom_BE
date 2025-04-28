package ddotcom.ddotcom.service

import ddotcom.ddotcom.dto.UnivDtoRequest
import ddotcom.ddotcom.entity.University
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class UnivService(
    @Qualifier("univMongoTemplate") private val univMongoTemplate: MongoTemplate // University 데이터는 productMongoTemplate 사용
) {

    /**
     * 이메일 도메인 중복 확인
     */
    // 이메일 도메인으로 대학교 조회
    fun findUniversityByEmailDomain(emailDomain: String): University? {
        val query = Query(Criteria.where("EmailDomain").`is`(emailDomain))
        return univMongoTemplate.findOne(query, University::class.java, "universities")
    }

    /**
     * 대학교 등록
     */
    fun addUniversity(univDtoRequest: UnivDtoRequest): String {
        // 이메일 도메인 중복 확인
        val existingUniversity = findUniversityByEmailDomain(univDtoRequest.emailDomain)
        if (existingUniversity != null) {
            return "이미 등록된 대학교입니다."
        }

        // 새로운 대학교 엔티티 생성 및 저장
        val university = University(
            id = null, // MongoDB에서 자동 생성
            emailDomain = univDtoRequest.emailDomain,
            name = univDtoRequest.name,
            dormitories = univDtoRequest.dormitories
        )
        univMongoTemplate.save(university, "universities")

        return "대학교가 성공적으로 등록되었습니다."
    }

    // 대학교 이름으로 기숙사 목록 조회 (추가 메서드)
    fun getDormitoriesByUniversity(universityName: String): List<String> {
        val query = Query(Criteria.where("name").`is`(universityName))
        val university = univMongoTemplate.findOne(query, University::class.java, "universities")
        return university?.dormitories ?: emptyList()
    }
}