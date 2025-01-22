package me.janek.weddingmuch.domain.authority

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
  private val jwtProvider: JwtProviderImpl
) : OncePerRequestFilter() {

  companion object {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val TOKEN_PREFIX = "Bearer "
  }

  private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val token = getAccessToken(request.getHeader(HEADER_AUTHORIZATION))

    if (token.isNotEmpty() && jwtProvider.verifyToken(token)) {
      jwtProvider.getAuthentication(token).let { SecurityContextHolder.getContext().authentication = it }
    } else {
      log.info("토큰이 유효하지 않습니다.")
    }

    filterChain.doFilter(request, response)
  }

  private fun getAccessToken(authorizationHeader: String?) =
    if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX))
      authorizationHeader.substring(TOKEN_PREFIX.length)
    else
      ""

}