import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
  kotlin("plugin.jpa") version "1.8.22"
  kotlin("kapt") version "1.8.22"
  idea
}

group = "me.janek"
version = "0.0.1-SNAPSHOT"

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
  kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
  kapt ("jakarta.annotation:jakarta.annotation-api")
  kapt ("jakarta.persistence:jakarta.persistence-api")
}

idea {
  module {
    val kaptMain = file("build/generated/source/kapt/main")
    sourceDirs.add(kaptMain)
    generatedSourceDirs.add(kaptMain)
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

