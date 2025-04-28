package ddotcom.ddotcom.member.service

import ddotcom.ddotcom.common.status.ErrorCode
import ddotcom.ddotcom.common.status.UserSignUpResponseCode
import ddotcom.ddotcom.member.dto.ResponseWrapper
import ddotcom.ddotcom.exception.CustomException
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailService(
    private val emailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
    private val redisService: RedisService,
    private val univService: UnivService
) {

    @Value("\${spring.mail.username}")
    private lateinit var from: String

    /**
     * 이메일 인증 코드 전송 (비동기 처리)
     */
    @Async
    fun sendAuthEmail(email: String, authCode: String): ResponseWrapper<Unit> {
        return try {
            val emailForm = makeEmailForm(email, authCode)

            // Redis에 기존 인증 코드가 있다면 삭제 후 새로운 코드 저장
            if (redisService.existData(email)) {
                redisService.deleteData(email)
            }

            emailSender.send(emailForm)
            redisService.setDataExpire(email, authCode, 3 * 60 * 1000L) // 만료 시간 3분

            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = UserSignUpResponseCode.SUCCESS.message,
                data = null
            )
        } catch (e: Exception) {
            throw CustomException(ErrorCode.BAD_REQUEST, e.message ?: "An error occurred while sending the email.")
        }
    }

    /**
     * 이메일 폼 생성
     */
    @Throws(MessagingException::class)
    private fun makeEmailForm(email: String, authCode: String): MimeMessage {
        val message = emailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, email) // 수신자 설정
        message.subject = "디닷컴 회원가입 이메일 인증" // 제목 설정
        message.setFrom(from) // 발신자 설정
        message.setContent(setContext(authCode), "text/html;charset=euc-kr") // HTML 내용 설정

        return message
    }

    /**
     * HTML 내용 생성 및 인증 코드 포함
     */
    private fun setContext(authCode: String): String {
        val context = Context()
        context.setVariable("code", authCode)
        return templateEngine.process("email", context)
    }

    /**
     * 인증 코드 생성 (8자리 랜덤 문자열)
     */
    fun makeAuthCode(): String {
        val random = java.util.Random()
        val key = StringBuilder()

        repeat(8) {
            when (random.nextInt(3)) {
                0 -> key.append((random.nextInt(26) + 97).toChar()) // 소문자 추가
                1 -> key.append((random.nextInt(26) + 65).toChar()) // 대문자 추가
                2 -> key.append(random.nextInt(10)) // 숫자 추가
            }
        }
        return key.toString()
    }
    /**
     * 이메일 인증 코드 확인 및 대학교 매핑 로직 추가
     */
    fun verifyEmailCodeAndMapUniversity(email: String, code: String): Pair<Boolean, String?> {
        val userMailCode = redisService.getData(email)

        if (userMailCode != code) {
            return Pair(false, null) // 인증 실패 시 false 반환
        }

        // 이메일 도메인 추출 및 대학교 매핑
        val emailDomain = email.substringAfter("@")
        val university = univService.findUniversityByEmailDomain(emailDomain)

        return Pair(true, university?.name) // 인증 성공 시 대학교 이름 반환
    }
    /**
     * 이메일 인증 코드 확인 (Redis에서 코드 검증)
     */
    fun verifyEmailCode(request: HttpServletRequest, email: String, code: String): ResponseWrapper<Unit> {
        val userMailCode = redisService.getData(email)

        return when {
            userMailCode == null -> ResponseWrapper(
                request = request,
                status = HttpStatus.BAD_REQUEST,
                success = false,
                message = UserSignUpResponseCode.EXPIRED_AUTH_MAIL_CODE.message,
                data = null
            )
            userMailCode == code -> ResponseWrapper(
                request = request,
                status = HttpStatus.OK,
                success = true,
                message = UserSignUpResponseCode.SUCCESS.message,
                data = null
            )
            else -> ResponseWrapper(
                request = request,
                status = HttpStatus.UNAUTHORIZED,
                success = false,
                message = UserSignUpResponseCode.INVALID_AUTH_MAIL_CODE.message,
                data = null
            )
        }
    }
}
