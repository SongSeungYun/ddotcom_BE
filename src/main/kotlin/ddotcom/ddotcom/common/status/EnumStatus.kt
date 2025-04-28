package ddotcom.ddotcom.common.status

enum class ROLE{
    MEMBER,
    ADMIN
}

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.")
}