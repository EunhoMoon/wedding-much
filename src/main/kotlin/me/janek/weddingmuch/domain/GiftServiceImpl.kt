package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.CreateRequest
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
  override fun saveNewGift(createRequest: CreateRequest) {
    val newGift = Gift.of(createRequest)
    giftRepository.save(newGift)
  }

  override fun getGiftList(pageCond: PageCond): List<Gift> = giftRepository.findAllGifts(pageCond)

  override fun getTotalGift(): Long = giftRepository.count()

  @Transactional
  override fun deleteGift(giftToken: String) = giftRepository.deleteByToken(giftToken)

}