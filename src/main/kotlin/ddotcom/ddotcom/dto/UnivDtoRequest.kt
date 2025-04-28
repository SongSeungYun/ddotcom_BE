package ddotcom.ddotcom.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UnivDtoRequest(
    @field:NotBlank
    @JsonProperty("email_domain")
    private val _emailDomain: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @JsonProperty("dormitories")
    @field:Size(min = 1, message = "최소 하나 이상의 기숙사 이름을 입력해야 합니다.")
    private val _dormitories: List<String>?
) {
    val emailDomain: String
        get() = _emailDomain!!

    val name: String
        get() = _name!!

    val dormitories: List<String>
        get() = _dormitories!!
}