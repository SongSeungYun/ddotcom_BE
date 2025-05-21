package ddotcom.ddotcom.config

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MultipleDatabaseConfig(
    private val mongoMappingContext: MongoMappingContext
) {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var mongoUri: String

    // 공통 MongoDatabaseFactory 생성 메서드
    fun createDatabaseFactory(databaseName: String): MongoDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(
            MongoClients.create(ConnectionString(mongoUri)),
            databaseName
        )
    }

    // 공통 MappingMongoConverter 생성 메서드
    fun createMappingMongoConverter(
        mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null)) // "_class" 필드 제거
        return converter
    }

    // 공통 MongoTemplate 생성 메서드
    fun createMongoTemplate(
        mongoDatabaseFactory: MongoDatabaseFactory,
        mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return MongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }

    // Member Database 설정 (기본 데이터베이스로 설정)
    @Bean(name = ["memberDatabaseFactory"])
    @Primary // 기본 데이터베이스 팩토리로 지정
    fun memberDatabaseFactory(): MongoDatabaseFactory {
        return createDatabaseFactory("member")
    }

    @Bean(name = ["memberMappingMongoConverter"])
    @Primary
    fun memberMappingMongoConverter(
        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        return createMappingMongoConverter(mongoDatabaseFactory)
    }

    @Bean(name = ["memberMongoTemplate"])
    @Primary
    fun memberMongoTemplate(
        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("memberMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return createMongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }

    // Product Database 설정
    @Bean(name = ["productDatabaseFactory"])
    fun productDatabaseFactory(): MongoDatabaseFactory {
        return createDatabaseFactory("product")
    }

    @Bean(name = ["productMappingMongoConverter"])
    fun productMappingMongoConverter(
        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        return createMappingMongoConverter(mongoDatabaseFactory)
    }

    @Bean(name = ["productMongoTemplate"])
    fun productMongoTemplate(
        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("productMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return createMongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }

    // University Database 설정
    @Bean(name = ["univDatabaseFactory"])
    fun univDatabaseFactory(): MongoDatabaseFactory {
        return createDatabaseFactory("university")
    }

    @Bean(name = ["univMappingMongoConverter"])
    fun univMappingMongoConverter(
        @Qualifier("univDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        return createMappingMongoConverter(mongoDatabaseFactory)
    }

    @Bean(name = ["univMongoTemplate"])
    fun univMongoTemplate(
        @Qualifier("univDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("univMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return createMongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }

    // Chatroom Database 설정
    @Bean(name = ["chatDatabaseFactory"])
    fun chatDatabaseFactory(): MongoDatabaseFactory {
        return createDatabaseFactory("chatroom")
    }

    @Bean(name = ["chatMappingMongoConverter"])
    fun chatMappingMongoConverter(
        @Qualifier("chatDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        return createMappingMongoConverter(mongoDatabaseFactory)
    }

    @Bean(name = ["chatMongoTemplate"])
    fun chatMongoTemplate(
        @Qualifier("chatDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("chatMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return createMongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }
}
