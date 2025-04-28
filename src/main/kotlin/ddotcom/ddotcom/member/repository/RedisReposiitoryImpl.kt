package ddotcom.ddotcom.member.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisRepository {

    override fun getData(key: String): String? {
        val valueOperations: ValueOperations<String, String> = redisTemplate.opsForValue()
        return valueOperations[key]
    }

    override fun existData(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    override fun setDataExpire(key: String, value: String, timeout: Long) {
        val valueOperations: ValueOperations<String, String> = redisTemplate.opsForValue()
        val duration = Duration.ofMillis(timeout)
        valueOperations.set(key, value, duration)
    }

    override fun deleteData(key: String) {
        redisTemplate.delete(key)
    }
}