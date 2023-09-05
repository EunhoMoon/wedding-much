import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
  kotlin("plugin.jpa") version "1.8.22"
  kotlin("kapt") version "1.8.22"
  id("com.github.node-gradle.node") version "2.2.4"
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

  implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
  kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
  kapt("jakarta.annotation:jakarta.annotation-api")
  kapt("jakarta.persistence:jakarta.persistence-api")
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

node {
  version = "16.14.2"
  distBaseUrl = "https://nodejs.org/dist"

  workDir = file("${project.buildDir}/nodejs")
  nodeModulesDir = file("${project.projectDir}")
  npmWorkDir = file("${project.buildDir}/npm")
}

val installDependencies by tasks.registering(NpmTask::class) {
  setArgs(listOf("install"))
  setExecOverrides(closureOf<ExecSpec> {
    setWorkingDir("${project.projectDir}/frontend")
  })
}

val buildReactTask by tasks.registering(NpmTask::class) {
  dependsOn(installDependencies)
  setArgs(listOf("run", "build"))
  setExecOverrides(closureOf<ExecSpec> {
    setWorkingDir("${project.projectDir}/frontend")
  })
}

val copyTask by tasks.registering(Copy::class) {
  dependsOn(buildReactTask)
  from(file("${project.projectDir}/frontend/build"))
  into(file("${project.buildDir}/resources/main/static"))
}

tasks.bootJar {
  dependsOn(copyTask)
}