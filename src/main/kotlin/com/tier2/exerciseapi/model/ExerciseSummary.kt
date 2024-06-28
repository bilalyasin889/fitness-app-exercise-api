package com.tier2.exerciseapi.model

data class ExerciseSummary(
    val id: Int,
    val name: String,
    val gifUrl: String,
    val bodyPart: String,
    val equipment: String,
    val targetMuscle: String,
    val isFavorite: Boolean? = null
) {
    constructor(
        id: Int,
        name: String,
        gifUrl: String,
        bodyPart: String,
        equipment: String,
        targetMuscle: String
    ) : this(id, name, gifUrl, bodyPart, equipment, targetMuscle, null)
}