package me.janek.weddingmuch.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter constructor(
//  private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

  companion object {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val TOKEN_PREFIX = "Bearer "
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val authorizationHeader = request.getHeader(HEADER_AUTHORIZATION)
    val token = getAccessToken(authorizationHeader)
  }

  private fun getAccessToken(authorizationHeader: String): String {
    return authorizationHeader.startsWith(TOKEN_PREFIX).let {
      authorizationHeader.substring(TOKEN_PREFIX.length)
    }
  }

}