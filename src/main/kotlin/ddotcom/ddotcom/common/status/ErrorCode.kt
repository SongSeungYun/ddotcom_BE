package ddotcom.ddotcom.common.status

enum class ErrorCode(val code: String, val message: String) {
    BAD_REQUEST("400", "잘못된 요청"),
    UNAUTHORIZED("401", "인증되지 않은 사용자"),
    FORBIDDEN("403", "접근 권한 없음"),
    NOT_FOUND("404", "리소스를 찾을 수 없음"),
    INTERNAL_SERVER_ERROR("500", "서버 내부 오류")
}