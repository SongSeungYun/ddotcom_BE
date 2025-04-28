package ddotcom.ddotcom.exception

import ddotcom.ddotcom.common.status.ErrorCode
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.naming.AuthenticationException

class CustomException(
    val errorCode: ErrorCode,
    override val message: String?
) : RuntimeException(message){
    @ExceptionHandler(JwtException::class)
    fun handleJwtException(e: JwtException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 JWT 토큰입니다. : ${e.message}")
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패 : ${e.message}")
    }
}