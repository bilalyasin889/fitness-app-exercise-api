package com.tier2.exerciseapi.config


import com.tier2.exerciseapi.service.TokenService
import com.tier2.exerciseapi.service.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsServiceImpl
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val email = tokenService.extractEmailFromHeader(authHeader)
            if (email != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(email)
                if (tokenService.isValidFromHeader(authHeader, foundUser))
                    updateContext(foundUser, request)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}