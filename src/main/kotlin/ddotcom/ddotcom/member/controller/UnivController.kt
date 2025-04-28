package ddotcom.ddotcom.member.controller

import ddotcom.ddotcom.member.dto.ResponseWrapper
import ddotcom.ddotcom.member.dto.UnivDtoRequest
import ddotcom.ddotcom.member.entity.University
import ddotcom.ddotcom.member.service.UnivService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/university")
@RestController
class UnivController(
    private val univService: UnivService
) {
    // 대학교 등록
    @PostMapping("/add")
    fun addUniversity(@RequestBody univDtoRequest: UnivDtoRequest): String {
        return univService.addUniversity(univDtoRequest)
    }

    @GetMapping("/find")
    fun findUniversityByEmailDomain(@RequestParam emailDomain: String): University? {
        return univService.findUniversityByEmailDomain(emailDomain)
    }

    @GetMapping("/dormitories")
    fun getDormitories(@RequestParam universityName: String): ResponseEntity<ResponseWrapper<List<String>>> {
        val dormitories = univService.getDormitoriesByUniversity(universityName)
        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = "기숙사 목록 조회 성공",
                data = dormitories
            )
        )
    }

}