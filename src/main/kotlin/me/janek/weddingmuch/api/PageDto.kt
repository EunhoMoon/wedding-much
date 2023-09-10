package me.janek.weddingmuch.api

import com.fasterxml.jackson.annotation.JsonIgnore

class PageCond(

  var page: Long = 1,

  var size: Long = 10

) {
  val offset: Long = (page - 1) * size
}

open class Pageable<T>(

  var total: Long,

  @JsonIgnore
  var pageCond: PageCond,

  var list: List<T>

) {

  open var pageCount = total / pageCond.size + if (total % pageCond.page > 0) 1 else 0
    get() = if (field == 0L) 1 else field

  open var presentPages = pageCond.page

}