package ddotcom.ddotcom.repository

interface RedisRepository {
    fun getData(key: String): String?
    fun existData(key: String): Boolean
    fun setDataExpire(key: String, value: String, timeout: Long)
    fun deleteData(key: String)
}