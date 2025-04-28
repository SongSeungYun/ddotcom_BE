package ddotcom.ddotcom.member.repository

import ddotcom.ddotcom.member.entity.Member

interface MemberRepository{
    fun findMemberByLoginId(loginId: String): Member?
    fun isLoginIdAvailable(loginId: String): Boolean
    fun isNicknameAvailable(nickname: String): Boolean
}