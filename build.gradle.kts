plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.serialization") version "1.9.25"

	id("org.jetbrains.kotlin.plugin.noarg") version "1.9.25"
}

group = "ddotcom"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//mongodb atlas
	implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.3.0")
	implementation("org.mongodb:bson-kotlinx:5.3.0")
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("io.github.cdimascio:dotenv-kotlin:6.5.0")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	//유효성 검사
	implementation("org.springframework.boot:spring-boot-starter-validation:3.4.2")
	//메일 인증
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	//redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	//시큐리티
	implementation("org.springframework.boot:spring-boot-starter-security")
	//jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	//swagger ui 설정
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	//웹소켓(채팅 기능)
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	//코틀린 serialization
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("org.springframework.data.mongodb.core.mapping.Document")
 }

noArg {
	annotation("org.springframework.data.mongodb.core.mapping.Document")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Java 9+ 모듈 시스템 호환성 문제를 해결하기 위해 JVM 옵션 추가
tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    jvmArgs = listOf("--add-opens=java.base/java.time=ALL-UNNAMED")
}
