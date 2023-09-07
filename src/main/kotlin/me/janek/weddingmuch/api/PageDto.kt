package me.janek.weddingmuch.api

import com.fasterxml.jackson.annotation.JsonIgnore

class PageCond(

  var page: Long = 1,

  var size: Long = 10

) {
  val offset: Long = (page - 1) * size
}

class Pageable<T>(

  val total: Long,

  @JsonIgnore
  val pageCond: PageCond,

  val list: List<T>

) {

  val pageCount = total / pageCond.size + if (total % pageCond.page > 0) 1 else 0
    get() = if (field == 0L) 1 else field

  val presentPages = pageCond.page

}