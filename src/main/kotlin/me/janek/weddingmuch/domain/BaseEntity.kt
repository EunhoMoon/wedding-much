package me.janek.weddingmuch.domain

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

  @CreatedDate
  var createdAt: LocalDateTime? = null
    protected set

  @LastModifiedDate
  var updatedAt: LocalDateTime? = null
    protected set

  @PrePersist
  fun onPrePersist() {
    val now = LocalDateTime.now()
    createdAt = now
    updatedAt = now
  }

  @PreUpdate
  fun onPreUpdate() {
    updatedAt = LocalDateTime.now()
  }

}