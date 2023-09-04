package me.janek.weddingmuch.domain.gift

import org.springframework.data.jpa.repository.JpaRepository

interface GiftRepository : JpaRepository<Gift, Long>