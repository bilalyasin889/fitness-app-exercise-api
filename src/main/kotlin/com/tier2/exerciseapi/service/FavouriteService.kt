package com.tier2.exerciseapi.service

import com.tier2.fitness.common.repository.UserRepository
import com.tier2.exerciseapi.entity.Favourite
import com.tier2.exerciseapi.model.ExerciseSummary
import com.tier2.exerciseapi.repository.ExerciseRepository
import com.tier2.exerciseapi.repository.FavouriteRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavouriteService(
    val favouriteRepository: FavouriteRepository,
    val userRepository: UserRepository,
    val exerciseRepository: ExerciseRepository
) {

    @Transactional(readOnly = true)
    fun getFavouritesForUser(email: String): List<ExerciseSummary> {
        userRepository.findUserByEmail(email)
            ?: throw EntityNotFoundException("User not found with email: $email")

        return favouriteRepository.findFavouriteExercisesByUserEmail(email)
    }

    @Transactional
    fun addFavourite(email: String, exerciseId: Int) {
        val user = userRepository.findUserByEmail(email)
            ?: throw EntityNotFoundException("User not found with email: $email")

        val exercise = exerciseRepository.findById(exerciseId)
            .orElseThrow { EntityNotFoundException("Exercise not found with ID: $exerciseId") }

        if (favouriteRepository.existsByUserIdAndExerciseId(user.id, exerciseId)) {
            throw IllegalStateException("Exercise already favourited by user")
        }

        val favourite = Favourite(user = user, exercise = exercise)
        favouriteRepository.save(favourite)
    }

    @Transactional
    fun removeFavourite(email: String, exerciseId: Int) {
        val user = userRepository.findUserByEmail(email)
            ?: throw EntityNotFoundException("User not found with email: $email")

        exerciseRepository.findById(exerciseId)
            .orElseThrow { EntityNotFoundException("Exercise not found with ID: $exerciseId") }

        val favourite = favouriteRepository.findByUserIdAndExerciseId(user.id, exerciseId)
            .firstOrNull() ?: throw EntityNotFoundException("User has not favourited this exercise")

        favouriteRepository.delete(favourite)
    }
}