package ddotcom.ddotcom.controller

import ddotcom.ddotcom.dto.EmailDtoRequest
import ddotcom.ddotcom.dto.ResponseWrapper
import ddotcom.ddotcom.service.EmailService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/email")
class EmailController(
    private val emailService: EmailService,
) {

    /**
     * 이메일 인증 코드 전송 API (POST /api/email/send)
     */
    @PostMapping("/send")
    fun confirmEmailAuth(@RequestBody dto: EmailDtoRequest): ResponseEntity<ResponseWrapper<Unit>> {
        // 인증 코드 생성 및 비동기 전송 처리
        val authCode = emailService.makeAuthCode()
        val response = emailService.sendAuthEmail(dto.email, authCode)

        return ResponseEntity.ok(response)
    }

    /**
     * 이메일 인증 코드 확인 API (GET /api/email/auth)
     */
    @GetMapping("/auth")
    fun checkMailCode(
        request: HttpServletRequest,
        @RequestParam("receiver") receiver: String,
        @RequestParam("code") code: String,
    ): ResponseEntity<ResponseWrapper<Unit>> {
        val response = emailService.verifyEmailCode(request, receiver, code)
        return ResponseEntity.status(response.status).body(response)
    }
}
