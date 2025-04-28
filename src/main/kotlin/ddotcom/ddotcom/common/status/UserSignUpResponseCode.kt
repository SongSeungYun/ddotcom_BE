//메일 발송 때 쓰는거임
package ddotcom.ddotcom.common.status

enum class UserSignUpResponseCode(val code: Int, val message: String) {
    SUCCESS(200, "Success"),
    MAIL_SEND_FAILED(500, "Mail send failed"),
    EXPIRED_AUTH_MAIL_CODE(400, "Expired authentication mail code"),
    INVALID_AUTH_MAIL_CODE(401, "Invalid authentication mail code")
}