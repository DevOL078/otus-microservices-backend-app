package ru.oleynik.otus.backend.app.config.security

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val jwtConfig: JwtConfig,
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val token = request.getHeader(AUTHORIZATION_HEADER)
                .replace(BEARER_PREFIX, "")
                .trim()

            val jwtParser = Jwts.parser()
                .verifyWith(jwtConfig.publicKey)
                .build()

            val signed = jwtParser.isSigned(token)
            if (signed) {
                val jwt = jwtParser.parse(token)

                val userId = ((jwt.payload as Map<*, *>)["userId"] as Int).toLong()
                val login = (jwt.payload as Map<*, *>)["sub"] as String

                val context = SecurityContextHolder.createEmptyContext()
                val userInfo = UserInfo(userId, login)

                val authToken = UsernamePasswordAuthenticationToken(
                    userInfo,
                    null,
                    userInfo.authorities)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)

                filterChain.doFilter(request, response)
            } else {
                response.status = HttpStatus.FORBIDDEN.value()
                return
            }
        } catch (e: Exception) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return EXCLUDE_URLS.contains(request.requestURI)
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer"

        val EXCLUDE_URLS = listOf("/auth")
    }
}