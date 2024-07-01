package com.tier2.exerciseapi.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.tier2.fitness.common.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "favourites")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Favourite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    val exercise: Exercise
)
