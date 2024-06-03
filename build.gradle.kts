import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
  kotlin("plugin.jpa") version "1.8.22"
  kotlin("kapt") version "1.8.22"
  id("com.github.node-gradle.node") version "2.2.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  idea
}

group = "me.janek"

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

  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  runtimeOnly("com.h2database:h2")
  runtimeOnly("com.mysql:mysql-connector-j")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
  kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
  kapt("jakarta.annotation:jakarta.annotation-api")
  kapt("jakarta.persistence:jakarta.persistence-api")

  // security
  implementation("org.springframework.boot:spring-boot-starter-security")

  // jwt
  implementation("io.jsonwebtoken:jjwt-api:0.12.5")
  implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
  implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

  // spring rest docs
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

val snippetsDir by extra { file("build/generated-snippets") }

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

tasks.bootJar {
  dependsOn()
}

tasks.jar {
  enabled = false
}

tasks {
  // Test 결과를 snippet Directory에 출력
  test {
    outputs.dir(snippetsDir)
  }

  asciidoctor {
    dependsOn(test)

    doFirst {
      delete(file("src/main/resources/static/docs"))
    }

    inputs.dir(snippetsDir)

    doLast {
      copy {
        from("build/docs/asciidoc")
        into("src/main/resources/static/docs")
      }
    }
  }

  build {
    dependsOn(asciidoctor)
  }
}