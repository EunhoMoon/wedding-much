package me.janek.weddingmuch.domain.gift

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.gift.GiftCreateRequest
import me.janek.weddingmuch.api.gift.GiftInfoResponse
import me.janek.weddingmuch.api.gift.GiftPageable
import me.janek.weddingmuch.api.gift.GiftUpdateRequest
import me.janek.weddingmuch.infrastructure.gift.GiftRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GiftServiceImpl(
  private val giftRepository: GiftRepository
) : GiftService {

  @Transactional
  override fun saveNewGift(createRequest: GiftCreateRequest) {
    Gift.of(createRequest, "1").let { giftRepository.save(it) }
  }

  @Transactional
  override fun updateGift(updateRequest: GiftUpdateRequest) {
    val gift = giftRepository.findByToken(updateRequest.token!!)
      ?: throw IllegalArgumentException("Gift not found with token: ${updateRequest.token}")
    gift.update(updateRequest)
  }

  override fun getAllGifts(pageCond: PageCond): GiftPageable {
    val allGifts = giftRepository.findAllGifts(pageCond).map(GiftInfoResponse.Companion::of)
    val total = giftRepository.count()

    return GiftPageable(total = total, pageCond = pageCond, list = allGifts)
  }

  @Transactional
  override fun deleteGift(giftToken: String) = giftRepository.deleteByToken(giftToken)

}