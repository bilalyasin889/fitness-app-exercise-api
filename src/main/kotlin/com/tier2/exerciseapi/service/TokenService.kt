package com.tier2.exerciseapi.service

import com.tier2.exerciseapi.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    jwtProperties: JwtProperties
) {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    private val accessTokenExpiration = jwtProperties.accessTokenExpiration

    fun generate(
        userDetails: UserDetails,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + accessTokenExpiration))
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    fun extractTokenFromHeader(authHeader: String?): String? {
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            return null
        }
        return authHeader.substring(7)
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun isValidFromHeader(authHeader: String?, userDetails: UserDetails): Boolean {
        val token = extractTokenFromHeader(authHeader) ?: return false
        return isValid(token, userDetails)
    }

    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    fun extractEmailFromHeader(authHeader: String): String? {
        val token = extractTokenFromHeader(authHeader) ?: return null

        return extractEmail(token)
    }

    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }
}