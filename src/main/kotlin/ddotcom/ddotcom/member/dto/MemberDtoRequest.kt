package ddotcom.ddotcom.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ddotcom.ddotcom.common.annotation.ValidEnum
import ddotcom.ddotcom.common.status.ROLE
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

//import java.time.LocalDate

//회원가입 dto
data class MemberDtoRequest(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @JsonProperty("phoneNumber")
    private val _phoneNumber: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    @field:NotBlank
    @JsonProperty("university")
    private val _university: String?,

    @field:NotBlank
    @JsonProperty("dormitory")
    private val _dormitory: String?,

    @ValidEnum(enumClass = ROLE::class, message = "유효하지 않은 ROLE 값입니다.")
    @JsonProperty("role")
    private val _role: ROLE?,

    @JsonProperty("nickname")
    @field:Pattern(
        regexp = "^[가-힣a-zA-Z0-9]{2,20}$",
        message = "닉네임은 한글, 영문, 숫자로 이루어진 2~20자리여야 합니다."
    )
    private val _nickname: String?,
){
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val phoneNumber: String
        get() = _phoneNumber!!
    val email: String
        get() = _email!!
    val university: String
        get() = _university!!
    val dormitory: String
        get() = _dormitory!!
    val role: ROLE
        get() = _role!!
    val nickname: String
        get() = _nickname!!
}

//로그인 dto
data class LoginDto(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}

//내 정보 조회시 응답 dto
data class MemberDtoResponse(
    val loginId: String,
    val name:String,
    val phoneNumber: String,
    val email: String,
    val university: String,
    val dormitory: String,
    val nickname: String
)

data class NicknameUpdateRequest(
    @field:NotBlank(message = "닉네임은 비어있으면 안됩니다.")
    @field:Pattern(
        regexp = "^[가-힣a-zA-Z0-9]{2,20}$",
        message = "닉네임은 한글, 영문, 숫자로 이루어진 2~20자리여야 합니다."
    )
    val newNickname: String
)

data class LoginIdUpdateRequest(
    @field:NotBlank(message = "로그인 아이디는 비어있으면 안됩니다.")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]{5,20}$",
        message = "로그인 아이디는 영문과 숫자로 이루어진 5~20자리여야 합니다."
    )
    val newLoginId: String
)

data class PasswordUpdateRequest(
    val currentPassword: String, // 현재 비밀번호
    val newPassword: String      // 새 비밀번호
)