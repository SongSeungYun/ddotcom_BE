package ddotcom.ddotcom.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MongoMappingContextConfig {

    @Bean
    fun mongoMappingContext(): MongoMappingContext {
        return MongoMappingContext()
    }
}