package com.tier2.exerciseapi.repository

import com.tier2.exerciseapi.entity.Favourite
import com.tier2.exerciseapi.model.ExerciseInfo
import com.tier2.exerciseapi.model.ExerciseSummary
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FavouriteRepository : CrudRepository<Favourite, Int> {

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, " +
                "CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END" +
                ") " +
                "FROM Exercise e " +
                "LEFT JOIN Favourite f ON e.id = f.exercise.id AND f.user.email = :email"
    )
    fun findAllSummariesWithFavorites(email: String): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseInfo(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, e.secondaryMuscles, e.instructions, " +
                "CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END" +
                ") " +
                "FROM Exercise e " +
                "LEFT JOIN Favourite f ON e.id = f.exercise.id AND f.user.email = :email " +
                "WHERE e.id = :id"
    )
    fun findExerciseInfoWithFavouriteById(id: Int, email: String): ExerciseInfo?

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, " +
                "CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END" +
                ") " +
                "FROM Exercise e " +
                "LEFT JOIN Favourite f ON e.id = f.exercise.id AND f.user.email = :email " +
                "WHERE e.targetMuscle = :targetMuscle"
    )
    fun findWithFavoritesByTargetMuscle(targetMuscle: String, email: String): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, " +
                "CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END" +
                ") " +
                "FROM Exercise e " +
                "LEFT JOIN Favourite f ON e.id = f.exercise.id AND f.user.email = :email " +
                "WHERE e.equipment = :equipment"
    )
    fun findWithFavoritesByEquipment(equipment: String, email: String): List<ExerciseSummary>

    @Query(
        "SELECT new com.tier2.exerciseapi.model.ExerciseSummary(" +
                "e.id, e.name, e.gifUrl, e.bodyPart, e.equipment, e.targetMuscle, TRUE" +
                ") " +
                "FROM Favourite f " +
                "JOIN f.exercise e " +
                "JOIN f.user u " +
                "WHERE u.email = :email"
    )
    fun findFavouriteExercisesByUserEmail(email: String): List<ExerciseSummary>

    fun findByUserIdAndExerciseId(userId: UUID, exerciseId: Int): List<Favourite>

    fun existsByUserIdAndExerciseId(userId: UUID, exerciseId: Int): Boolean
}