package com.tier2.exerciseapi.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "exercise")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Exercise(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0,

    @Column(name = "name", nullable = false, length = 225)
    val name: String,

    @Column(name = "gif_url", nullable = false, length = 225)
    val gifUrl: String,

    @Column(name = "body_part", nullable = false, length = 225)
    val bodyPart: String,

    @Column(name = "target_muscle", nullable = false, length = 255)
    val targetMuscle: String,

    @Column(name = "equipment", nullable = false, length = 225)
    val equipment: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "secondary_muscles", columnDefinition = "json", nullable = false)
    val secondaryMuscles: List<String>,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "instructions", columnDefinition = "json", nullable = false)
    val instructions: List<String>
)