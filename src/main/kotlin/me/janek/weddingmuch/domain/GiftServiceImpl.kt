package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.GiftCreateRequest
import me.janek.weddingmuch.api.GiftUpdateRequest
import me.janek.weddingmuch.api.PageCond
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
    val gift = giftRepository.findByToken(updateRequest.token!!) ?: throw IllegalArgumentException("Gift not found with token: ${updateRequest.token}")
    gift.update(updateRequest)
  }

  override fun getGiftList(pageCond: PageCond): List<Gift> = giftRepository.findAllGifts(pageCond)

  override fun getTotalGift(): Long = giftRepository.count()

  @Transactional
  override fun deleteGift(giftToken: String) = giftRepository.deleteByToken(giftToken)

}