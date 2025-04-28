package ddotcom.ddotcom

import ddotcom.ddotcom.config.MailConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
//	(exclude = [
//	org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration::class,
//	org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration::class
//])
@EnableConfigurationProperties(MailConfig::class)

class DdotcomApplication

fun main(args: Array<String>) {
	runApplication<DdotcomApplication>(*args)
}