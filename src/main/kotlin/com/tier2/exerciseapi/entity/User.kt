package com.tier2.exerciseapi.entity

import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import org.springframework.security.core.userdetails.User as SecurityUser

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    val id: UUID,

    @Column(name = "email", nullable = false, unique = true, length = 255)
    val email: String,

    @Column(name = "password", nullable = false, length = 255)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Column(name = "name", nullable = false, length = 255)
    var name: String,

    @Column(name = "weight", nullable = false)
    val weight: Double,

    @Column(name = "height", nullable = false)
    val height: Double
)

enum class Role {
    USER, ADMIN
}

fun User.mapToUserDetails(): UserDetails =
    SecurityUser.withUsername(this.email)
        .password(this.password)
        .roles(this.role.name)
        .build()
