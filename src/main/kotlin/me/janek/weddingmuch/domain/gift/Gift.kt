package me.janek.weddingmuch.domain.gift

import jakarta.persistence.*
import me.janek.weddingmuch.api.gift.GiftCreateRequest
import me.janek.weddingmuch.api.gift.GiftUpdateRequest
import me.janek.weddingmuch.domain.BaseEntity
import java.util.UUID

@Entity
@Table(name = "gifts")
class Gift(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var price: Int,

    var memo: String?,

    @Column(nullable = false)
    var userToken: String

) : BaseEntity() {

    init {
        if (name.isBlank()) throw IllegalArgumentException("이름을 입력해주세요.")
        if (userToken.isBlank()) throw IllegalArgumentException("사용자 토큰을 입력해주세요.")
    }

    companion object {
        fun of(request: GiftCreateRequest, userToken: String): Gift = Gift(
            name = request.name!!,
            memo = request.memo,
            price = request.price!!,
            token = UUID.randomUUID().toString(),
            userToken = userToken,
        )
    }

    fun update(request: GiftUpdateRequest) {
        if (request.name.isNullOrBlank()) throw IllegalArgumentException("이름을 입력해주세요.")
        request.price ?: throw IllegalArgumentException("금액을 입력해주세요.")
        this.name = request.name
        this.memo = request.memo
        this.price = request.price
    }
}