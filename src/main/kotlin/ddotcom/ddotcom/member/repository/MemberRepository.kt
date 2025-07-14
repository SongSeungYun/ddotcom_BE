package ddotcom.ddotcom.member.repository

import ddotcom.ddotcom.member.entity.Member

interface MemberRepository{
    fun findById(id: String): Member? // 고유 ID로 멤버를 찾는 함수 추가
    fun findMemberByLoginId(loginId: String): Member?
    fun isLoginIdAvailable(loginId: String): Boolean
    fun isNicknameAvailable(nickname: String): Boolean
}
