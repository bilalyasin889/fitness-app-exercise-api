package com.tier2.exerciseapi.service

import com.tier2.exerciseapi.model.ExerciseInfo
import com.tier2.exerciseapi.model.ExerciseSummary
import com.tier2.exerciseapi.repository.ExerciseRepository
import com.tier2.exerciseapi.repository.FavouriteRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseService(
    val exerciseRepository: ExerciseRepository,
    val favouriteRepository: FavouriteRepository
) {

    @Transactional(readOnly = true)
    fun getAllExercises(): List<ExerciseSummary> = exerciseRepository.findAllSummaries()

    @Transactional(readOnly = true)
    fun getAllExercises(email: String): List<ExerciseSummary> =
        favouriteRepository.findAllSummariesWithFavorites(email)

    @Transactional(readOnly = true)
    fun getExerciseById(id: Int): ExerciseInfo? = exerciseRepository.findExerciseInfoById(id)
        ?: throw EntityNotFoundException("Exercise not found with id: $id")

    @Transactional(readOnly = true)
    fun getExerciseById(id: Int, email: String) = favouriteRepository.findExerciseInfoWithFavouriteById(id, email)
        ?: throw EntityNotFoundException("Exercise not found with id: $id")

    @Transactional(readOnly = true)
    fun getExercisesByBodyPart(bodyPart: String): List<ExerciseSummary> = exerciseRepository.findByBodyPart(bodyPart)

    @Transactional(readOnly = true)
    fun getExercisesByTargetMuscle(targetMuscle: String): List<ExerciseSummary> =
        exerciseRepository.findByTargetMuscle(targetMuscle)

    @Transactional(readOnly = true)
    fun getExercisesByTargetMuscle(targetMuscle: String, email: String): List<ExerciseSummary> =
        favouriteRepository.findWithFavoritesByTargetMuscle(targetMuscle, email)

    @Transactional(readOnly = true)
    fun getExercisesByEquipment(equipment: String): List<ExerciseSummary> =
        exerciseRepository.findByEquipment(equipment)

    @Transactional(readOnly = true)
    fun getExercisesByEquipment(equipment: String, email: String): List<ExerciseSummary> =
        favouriteRepository.findWithFavoritesByEquipment(equipment, email)

    @Transactional(readOnly = true)
    fun getBodyParts(): List<String> = exerciseRepository.findAllBodyParts().sorted()

    @Transactional(readOnly = true)
    fun getEquipmentList(): List<String> = exerciseRepository.findAllEquipment().sorted()

}