package ddotcom.ddotcom.repository

import ddotcom.ddotcom.entity.Member

interface MemberRepository{
    fun findMemberByLoginId(loginId: String): Member?
    fun isLoginIdAvailable(loginId: String): Boolean
    fun isNicknameAvailable(nickname: String): Boolean
}