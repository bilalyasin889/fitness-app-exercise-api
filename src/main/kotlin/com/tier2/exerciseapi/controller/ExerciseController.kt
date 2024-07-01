package com.tier2.exerciseapi.controller

import com.tier2.fitness.common.service.TokenService
import com.tier2.exerciseapi.model.ExerciseSummary
import com.tier2.exerciseapi.service.ExerciseService
import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/exercises")
class ExerciseController(
    val exerciseService: ExerciseService,
    private val tokenService: TokenService
) {

    private val logger: Logger = LoggerFactory.getLogger(ExerciseController::class.java)

    @GetMapping("/all")
    fun getExercises(): ResponseEntity<List<ExerciseSummary>> = ResponseEntity.ok(exerciseService.getAllExercises())

    @GetMapping("/with-favourite/all")
    fun getExercisesWithFavorites(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request for all exercises with email [{}]", email)

            return ResponseEntity.ok(exerciseService.getAllExercises(email))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find favourites. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @GetMapping("/{id}")
    fun getExerciseById(@PathVariable id: Int): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(exerciseService.getExerciseById(id))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find exercise info. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @GetMapping("/with-favourite/{id}")
    fun getExerciseById(
        @PathVariable id: Int,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request for exercise info with id [{}] with email [{}]", id, email)

            ResponseEntity.ok(exerciseService.getExerciseById(id, email))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find exercise info with favourite info. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @GetMapping("/body-part/{bodyPart}")
    fun getExercisesByBodyPart(@PathVariable bodyPart: String): ResponseEntity<List<ExerciseSummary>> =
        ResponseEntity.ok(exerciseService.getExercisesByBodyPart(bodyPart))

    @GetMapping("/target-muscle/{targetMuscle}")
    fun getExercisesByTargetMuscle(@PathVariable targetMuscle: String): ResponseEntity<List<ExerciseSummary>> =
        ResponseEntity.ok(exerciseService.getExercisesByTargetMuscle(targetMuscle))

    @GetMapping("/with-favourite/target-muscle/{targetMuscle}")
    fun getExercisesByTargetMuscle(
        @PathVariable targetMuscle: String,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request for similar target exercises with for target [{}] with email [{}]", targetMuscle, email)

            ResponseEntity.ok(exerciseService.getExercisesByTargetMuscle(targetMuscle, email))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find similar target exercises. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @GetMapping("/equipment/{equipment}")
    fun getExercisesByEquipment(@PathVariable equipment: String): ResponseEntity<List<ExerciseSummary>> =
        ResponseEntity.ok(exerciseService.getExercisesByEquipment(equipment))

    @GetMapping("/with-favourite/equipment/{equipment}")
    fun getExercisesByEquipment(
        @PathVariable equipment: String,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request for similar equipment exercises with for equipment [{}] with email [{}]", equipment, email)

            ResponseEntity.ok(exerciseService.getExercisesByEquipment(equipment, email))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find similar equipment exercises. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @GetMapping("/body-parts")
    fun getBodyParts(): ResponseEntity<List<String>> = ResponseEntity.ok(exerciseService.getBodyParts())

    @GetMapping("/equipments")
    fun getEquipmentList(): ResponseEntity<List<String>> = ResponseEntity.ok(exerciseService.getEquipmentList())
}