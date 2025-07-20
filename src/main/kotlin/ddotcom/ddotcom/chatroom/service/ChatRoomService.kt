package ddotcom.ddotcom.chatroom.service

import ddotcom.ddotcom.chatroom.dto.ChatRoomCreateRequest
import ddotcom.ddotcom.chatroom.entity.ChatRoom
import ddotcom.ddotcom.chatroom.repository.ChatRoomRepository
import ddotcom.ddotcom.member.repository.MemberRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val memberRepository: MemberRepository // 사용자 검증을 위해 주입
) {

    @Transactional
    fun createChatRoom(request: ChatRoomCreateRequest, authenticatedUserId: String): ChatRoom {
        // 0. 중복 생성 방지: 해당 productId로 생성된 채팅방이 이미 있는지 확인
        chatRoomRepository.findByProductId(request.productId)?.let {
            throw IllegalStateException("이미 해당 상품에 대한 채팅방이 존재합니다.")
        }

        // [디버깅 로그] 실제로 비교되는 두 값을 서버 콘솔에 출력합니다.
        println("--- DEBUG --- ")
        println("Authenticated User ID (from Token): $authenticatedUserId")
        println("Leader ID (from Request Body):    ${request.leaderId}")
        println("Are they equal? ${authenticatedUserId == request.leaderId}")
        println("-------------")

        // 1. 보안 검증: 요청을 보낸 사용자가 DTO에 명시된 리더와 일치하는지 확인
        if (authenticatedUserId != request.leaderId) {
            throw AccessDeniedException("채팅방 생성은 리더로 지정된 사용자 본인만 가능합니다.")
        }

        // 2. 유효성 검증: 리더로 지정된 사용자가 실제 DB에 존재하는지 확인
        memberRepository.findById(request.leaderId)
            ?: throw IllegalArgumentException("리더로 지정된 사용자를 찾을 수 없습니다. id=${request.leaderId}")

        // 3. 데이터 일관성 보장: 리더는 항상 참여자 목록에 포함되도록 처리
        val finalParticipantIds = (request.participantIds + request.leaderId).distinct()

        // 4. DTO를 기반으로 ChatRoom 엔티티 생성
        val chatRoom = ChatRoom(
            productId = request.productId,
            status = request.status,
            leaderId = request.leaderId,
            participantIds = finalParticipantIds
        )
        return chatRoomRepository.save(chatRoom)
    }

    @Transactional
    fun joinChatRoom(roomId: String, authenticatedUserId: String) {
        val chatRoom = chatRoomRepository.findById(roomId) ?: throw IllegalArgumentException("해당 채팅방을 찾을 수 없습니다. id=$roomId")
        val member = memberRepository.findById(authenticatedUserId)
            ?: throw IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=$authenticatedUserId")

        // 이미 참여중이 아니라면, 새로운 참여자 목록으로 copy 후 저장
        if (!chatRoom.participantIds.contains(member._id)) {
            val updatedParticipants = chatRoom.participantIds + member._id!!
            val updatedChatRoom = chatRoom.copy(participantIds = updatedParticipants)
            chatRoomRepository.save(updatedChatRoom)
        }
    }

    @Transactional
    fun leaveChatRoom(roomId: String, memberId: String) {
        val chatRoom = chatRoomRepository.findById(roomId) ?: throw IllegalArgumentException("해당 채팅방을 찾을 수 없습니다. id=$roomId")
        val member = memberRepository.findById(memberId)
            ?: throw IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=$memberId")

        // 참여자가 아니면 아무것도 하지 않음
        if (!chatRoom.participantIds.contains(member._id!!)) {
            return
        }

        val updatedParticipants = chatRoom.participantIds - member._id!!

        if (updatedParticipants.isEmpty()) {
            chatRoomRepository.delete(chatRoom)
        } else {
            // 나간 사용자가 방장이었다면, 남은 참여자 중 첫 번째 사람에게 방장 위임
            val newLeaderId = if (chatRoom.leaderId == member._id) {
                updatedParticipants.first()
            } else {
                chatRoom.leaderId
            }
            val updatedChatRoom = chatRoom.copy(
                leaderId = newLeaderId,
                participantIds = updatedParticipants
            )
            chatRoomRepository.save(updatedChatRoom)
        }
    }

    fun findMyChatRooms(memberId: String): List<ChatRoom> {
        val member = memberRepository.findById(memberId)
            ?: throw IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=$memberId")
        return chatRoomRepository.findByParticipantIdsContaining(member._id!!)
    }

    fun findById(id: String): ChatRoom? {
        return chatRoomRepository.findById(id)
    }
}


