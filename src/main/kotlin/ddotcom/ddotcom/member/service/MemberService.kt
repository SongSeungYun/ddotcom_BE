package ddotcom.ddotcom.member.service

//import ddotcom.ddotcom.database.MultipleMongoConfig
import ddotcom.ddotcom.common.authority.JwtTokenProvider
import ddotcom.ddotcom.common.exception.InvalidInputException
import ddotcom.ddotcom.common.status.ROLE
import ddotcom.ddotcom.member.dto.*
import ddotcom.ddotcom.member.entity.Member
import ddotcom.ddotcom.member.repository.MemberRepositoryImpl
//import ddotcom.ddotcom.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    @Qualifier("memberMongoTemplate") private val memberMongoTemplate: MongoTemplate,
    @Qualifier("productMongoTemplate") private val productMongoTemplate: MongoTemplate,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val memberRepository: MemberRepositoryImpl
) {
    fun getMemberByLoginId(loginId: String): Member? {
        return memberRepository.findMemberByLoginId(loginId)
    }

    fun checkLoginIdAvailability(loginId: String): Boolean {
        return memberRepository.isLoginIdAvailable(loginId)
    }

    fun checkNicknameAvailability(nickname: String): Boolean {
        return memberRepository.isNicknameAvailable(nickname)
    }

    //회원가입
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        println("Received DTO: $memberDtoRequest") // 디버깅용 로그 추가

        if (memberDtoRequest.loginId.isBlank() ||
            memberDtoRequest.password.isBlank() ||
            memberDtoRequest.name.isBlank() ||
            memberDtoRequest.phoneNumber.isBlank() ||
            memberDtoRequest.email.isBlank() ||
            memberDtoRequest.university.isBlank() ||
            memberDtoRequest.dormitory.isBlank()
        ) {
            return "필수 입력값이 누락되었습니다."
        }
        // loginId 중복 확인
        if (!memberRepository.isLoginIdAvailable(memberDtoRequest.loginId)) {
            return "이미 등록된 아이디입니다."
        }

        if(!memberRepository.isNicknameAvailable(memberDtoRequest.nickname)) {
            return "이미 등록된 닉네임입니다."
        }

        // 회원 정보 저장
        val member = Member(
            _id = null, // MongoDB 자동 생성
            loginId = memberDtoRequest.loginId,
            password = passwordEncoder.encode(memberDtoRequest.password),
            name = memberDtoRequest.name,
            phoneNumber = memberDtoRequest.phoneNumber,
            email = memberDtoRequest.email,
            university = memberDtoRequest.university, // 이메일 인증 후 매핑된 대학교 이름 사용
            dormitory = memberDtoRequest.dormitory, // 사용자가 선택한 기숙사 이름 사용
            role = ROLE.MEMBER,
            nickname = memberDtoRequest.nickname
        )
        memberMongoTemplate.save(member, "member_info")

        return "회원가입이 완료되었습니다."
    }

    //로그인
    fun login(loginDto: LoginDto): String {
        // 입력값 검증
        if (loginDto.loginId.isBlank() || loginDto.password.isBlank()) {
            return "로그인 아이디와 비밀번호는 비어있으면 안됩니다."
        }

        // loginId로 회원 조회
        val query = Query(Criteria.where("loginId").`is`(loginDto.loginId))
        val member = memberMongoTemplate.findOne(query, Member::class.java, "member_info")

        // 아이디 존재 여부 확인
        if (member == null || !passwordEncoder.matches(loginDto.password, member.password)) {
            return "잘못된 로그인 아이디 또는 비밀번호입니다."
        }

        // JWT 토큰 생성
        return jwtTokenProvider.generateToken(member.loginId, listOf(member.role.name))
    }

    fun updateLoginId(username: String, loginIdUpdateRequest: LoginIdUpdateRequest) {
        // 현재 로그인된 사용자의 정보 조회
        val member = memberRepository.findMemberByLoginId(username)
            ?: throw InvalidInputException("loginId", "회원 정보를 찾을 수 없습니다.")
        // 로그인 아이디 중복 체크
        if (!memberRepository.isLoginIdAvailable(loginIdUpdateRequest.newLoginId)) {
            throw InvalidInputException("loginId", "이미 사용 중인 아이디입니다.")
        }
        // 로그인 아이디 변경
        member.loginId = loginIdUpdateRequest.newLoginId
        // 변경된 정보 저장
        memberMongoTemplate.save(member, "member_info")
    }

    fun updateNickname(username: String, nicknameUpdateRequest: NicknameUpdateRequest) {
        // 현재 로그인된 사용자의 정보 조회
        val member = memberRepository.findMemberByLoginId(username)
            ?: throw InvalidInputException("loginId", "회원 정보를 찾을 수 없습니다.")
        // 닉네임 중복 체크
        if (!memberRepository.isNicknameAvailable(nicknameUpdateRequest.newNickname)) {
            throw InvalidInputException("nickname", "이미 사용 중인 닉네임입니다.")
        }
        // 닉네임 변경
        member.nickname = nicknameUpdateRequest.newNickname
        // 변경된 정보 저장
        memberMongoTemplate.save(member, "member_info")
    }

    //password 업데이트
    fun updatePassword(username: String, passwordUpdateRequest: PasswordUpdateRequest) {
        // 현재 로그인된 사용자의 정보 조회
        val member = memberRepository.findMemberByLoginId(username)
            ?: throw InvalidInputException("loginId", "회원 정보를 찾을 수 없습니다.")
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(passwordUpdateRequest.currentPassword, member.password)) {
            throw InvalidInputException("currentPassword", "현재 비밀번호가 일치하지 않습니다.")
        }
        // 새 비밀번호 유효성 검사
        val newPasswordPattern =
            Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$")
        if (!newPasswordPattern.matches(passwordUpdateRequest.newPassword)) {
            throw InvalidInputException(
                "newPassword",
                "새 비밀번호는 영문, 숫자, 특수문자를 포함한 8~20자리여야 합니다."
            )
        }
        // 새 비밀번호 설정 및 저장
        member.password = passwordEncoder.encode(passwordUpdateRequest.newPassword)
        memberMongoTemplate.save(member, "member_info")
    }
}