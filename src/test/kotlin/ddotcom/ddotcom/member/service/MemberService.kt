//package ddotcom.ddotcom.member.service
//
//import ddotcom.ddotcom.dto.MemberDtoRequest
//import ddotcom.ddotcom.entity.Member
//import ddotcom.ddotcom.member.repository.MemberRepository
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Transactional
//@Service
//class MemberService(
//    private val memberRepository: MemberRepository
//)
//{
//    /**
//     * 회원가입
//     */
//    fun signUp(memberDtoRequest: MemberDtoRequest): String {
//        // ID 중복 검사
//        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
//        if(member!=null){
//            return "이미 등록된 아이디입니다"
//        }
//
//        member = Member(
//            null,
//            memberDtoRequest.loginId,
//            memberDtoRequest.password,
//            memberDtoRequest.name,
//            memberDtoRequest.gender,
//            memberDtoRequest.email
//            )
//        memberRepository.save(member)
//
//        return "회원가입이 완료되었습니다."
//    }
//}