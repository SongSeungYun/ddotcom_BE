package ddotcom.ddotcom.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus

data class ResponseWrapper<T>(
    @JsonIgnore val request: HttpServletRequest?=null,
    val status: HttpStatus,
    val success: Boolean,
    val message: String,
    val data: T? = null // 데이터는 nullable로 처리 (옵션)
)