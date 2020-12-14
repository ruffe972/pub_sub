plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("java")
}

group = "ruffe972"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.4.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.4.1")
    // todo как точно надо указывать версию, т.е. используется ли semver здесь?
    runtimeOnly("org.postgresql:postgresql:42.2.18")
}
