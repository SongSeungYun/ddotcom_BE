//package ddotcom.ddotcom.member.controller
//
//import ddotcom.ddotcom.dto.MemberDtoRequest
//import ddotcom.ddotcom.member.service.MemberService
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RequestMapping("/api/member")
//@RestController
//class MemberController(
//    private val memberService: MemberService
//) {
//    /**
//     * 회원가입
//     */
//    @PostMapping("/signup")
//    fun signUp(@RequestBody memberDtoRequest: MemberDtoRequest): String {
//        return memberService.signUp(memberDtoRequest)
//    }
//}