package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.*
import me.janek.weddingmuch.infrastructure.GiftRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GiftServiceImpl(
  private val giftRepository: GiftRepository
) : GiftService {

  @Transactional
  override fun saveNewGift(createRequest: GiftCreateRequest) {
    val newGift = Gift.of(createRequest)
    giftRepository.save(newGift)
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