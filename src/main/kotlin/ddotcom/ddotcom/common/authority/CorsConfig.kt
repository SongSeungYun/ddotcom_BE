//package ddotcom.ddotcom.common.authority
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.web.filter.CorsFilter
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//
//@Configuration
//@ComponentScan
//@Order(Ordered.HIGHEST_PRECEDENCE)
//class CorsConfig {
//    @Bean
//    fun corsFilter(): CorsFilter {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.allowedOriginPatterns = listOf("http://localhost:3000") // ✅ 프론트엔드 주소 추가
//        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ 허용할 HTTP 메서드
//        config.allowedHeaders = listOf("*") // ✅ 모든 헤더 허용
//        config.allowCredentials = true // ✅ 인증 정보 포함 허용
//        source.registerCorsConfiguration("/**", config)
//        return CorsFilter(source)
//    }
//}