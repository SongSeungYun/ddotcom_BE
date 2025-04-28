//package ddotcom.ddotcom.controller
//
//import ddotcom.ddotcom.common.authority.JwtUtil
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/api/auth")
//class AuthController(
//    private val jwtUtil: JwtUtil,
//) {
//    @PostMapping("/login")
//    fun login(@RequestParam username: String): ResponseEntity<Map<String, String>> {
//        // 실제로는 사용자 인증 로직이 필요합니다 (예: DB 조회 등)
//        val token = jwtUtil.generateToken(username)
//        return ResponseEntity.ok(mapOf("token" to token))
//    }
//}