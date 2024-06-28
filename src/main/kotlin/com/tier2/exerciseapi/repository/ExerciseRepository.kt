package com.tier2.exerciseapi.repository

import com.tier2.exerciseapi.entity.Exercise
import com.tier2.exerciseapi.model.ExerciseInfo
import com.tier2.exerciseapi.model.ExerciseSummary
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface ExerciseRepository : CrudRepository<Exercise, Int> {

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle" +
                ") " +
                "FROM Exercise e"
    )
    fun findAllSummaries(): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseInfo(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, e.secondaryMuscles, e.instructions" +
                ") " +
                "FROM Exercise e " +
                "WHERE e.id = :id"
    )
    fun findExerciseInfoById(id: Int): ExerciseInfo?

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle" +
                ") " +
                "FROM Exercise e " +
                "WHERE e.bodyPart = :bodyPart"
    )
    fun findByBodyPart(bodyPart: String): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle" +
                ") " +
                "FROM Exercise e " +
                "WHERE e.targetMuscle = :targetMuscle"
    )
    fun findByTargetMuscle(targetMuscle: String): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle" +
                ") " +
                "FROM Exercise e " +
                "WHERE e.equipment = :equipment"
    )
    fun findByEquipment(equipment: String): List<ExerciseSummary>

    @Query("SELECT DISTINCT e.bodyPart FROM Exercise e")
    fun findAllBodyParts(): List<String>

    @Query("SELECT DISTINCT e.equipment FROM Exercise e")
    fun findAllEquipment(): List<String>

}