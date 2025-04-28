package ddotcom.ddotcom.member.service

import ddotcom.ddotcom.member.repository.RedisRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RedisService(
    private val redisRepositoryImpl: RedisRepositoryImpl
) {

    @Transactional(readOnly = true)
    fun getData(key: String): String? {
        return redisRepositoryImpl.getData(key)
    }

    @Transactional(readOnly = true)
    fun existData(key: String): Boolean {
        return redisRepositoryImpl.existData(key)
    }

    @Transactional(readOnly = false)
    fun setDataExpire(key: String, value: String, timeout: Long) {
        redisRepositoryImpl.setDataExpire(key, value, timeout)
    }

    @Transactional(readOnly = false)
    fun deleteData(key: String) {
        redisRepositoryImpl.deleteData(key)
    }
}