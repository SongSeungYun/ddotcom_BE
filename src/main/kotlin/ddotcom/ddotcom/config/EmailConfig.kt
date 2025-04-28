package ddotcom.ddotcom.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Component
import java.util.Properties

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
class MailConfig {
    var host: String = "localhost" // 기본값 설정
    var username: String = ""
    var password: String = ""
    var port: Int = 25 // 기본 SMTP 포트 (25)
    var auth: Boolean = false
    var debug: Boolean = false
    var connectionTimeout: Int = 0
    var startTlsEnable: Boolean = true

    @Bean
    fun javaMailService(): JavaMailSender {
        return JavaMailSenderImpl().apply {
            this.host = this@MailConfig.host
            this.username = this@MailConfig.username
            this.password = this@MailConfig.password
            this.port = this@MailConfig.port

            setDefaultEncoding("UTF-8")
            setJavaMailProperties(Properties().apply {
                put("mail.smtp.auth", auth.toString())
                put("mail.smtp.debug", debug.toString())
                put("mail.smtp.connectiontimeout", connectionTimeout.toString())
                put("mail.smtp.starttls.enable", startTlsEnable.toString())
            })
        }
    }
}
