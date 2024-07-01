package com.tier2.exerciseapi.controller

import com.tier2.fitness.common.service.TokenService
import com.tier2.exerciseapi.service.FavouriteService
import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/favourites")
class FavouriteController(
    val favouriteService: FavouriteService,
    private val tokenService: TokenService
) {
    private val logger: Logger = LoggerFactory.getLogger(FavouriteController::class.java)

    @GetMapping
    fun getUserFavourites(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request for user favourites for email [{}]", email)

            return ResponseEntity.ok(favouriteService.getFavouritesForUser(email))
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to find favourites. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @PostMapping("/add/{exerciseId}")
    fun addFavourite(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable exerciseId: Int
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request to add favourite exercise for email [{}]", email)

            favouriteService.addFavourite(email, exerciseId)
            ResponseEntity.ok("Favourite exercise added successfully")
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to add favourite. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }

    @DeleteMapping("/remove/{exerciseId}")
    fun removeFavourite(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable exerciseId: Int
    ): ResponseEntity<*> {
        return try {
            val email = tokenService.extractEmailFromHeader(authHeader)
                ?: throw EntityNotFoundException("No email provided")

            logger.info("Received request to remove favourite exercise for email [{}]", email)

            favouriteService.removeFavourite(email, exerciseId)
            ResponseEntity.ok("Favourite exercise removed successfully")
        } catch (ex: EntityNotFoundException) {
            logger.error("Failed to remove favourite. Reason: ${ex.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
        }
    }
}