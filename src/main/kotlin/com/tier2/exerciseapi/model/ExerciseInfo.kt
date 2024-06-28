package com.tier2.exerciseapi.model

data class ExerciseInfo(
    val id: Int,
    val name: String,
    val gifUrl: String,
    val bodyPart: String,
    val equipment: String,
    val targetMuscle: String,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
    val isFavorite: Boolean? = null
) {
    constructor(
        id: Int,
        name: String,
        gifUrl: String,
        bodyPart: String,
        equipment: String,
        targetMuscle: String,
        secondaryMuscles: List<String>,
        instructions: List<String>,
    ) : this(id, name, gifUrl, bodyPart, equipment, targetMuscle, secondaryMuscles, instructions,null)
}