package ru.wolfram.vote.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.vote.dto.TokenValidationResult
import ru.wolfram.vote.dto.VoteDto
import ru.wolfram.vote.dto.VoteDto2
import ru.wolfram.vote.service.AuthService
import ru.wolfram.vote.service.VotesService

@RestController
@RequestMapping("/api/v1/votes")
class VotesController {
    @Autowired
    private lateinit var votesService: VotesService

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping("/create-vote")
    fun createVote(
        @RequestBody votes: List<VoteDto>,
        @RequestParam token: String
    ): ResponseEntity<Unit> {
        if (authService.validateToken(token) !is TokenValidationResult.Success) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        votesService.saveVoteEntity(votes)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/do-vote")
    fun vote(
        @RequestBody voteDto: VoteDto,
        @RequestParam token: String,
    ): ResponseEntity<List<VoteDto2>> {
        val validationResult = authService.validateToken(token)
        if (validationResult !is TokenValidationResult.Success) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity.ok()
            .body(
                votesService.incrementVotesCount(
                    validationResult.claims.subject,
                    voteDto.title,
                    voteDto.variant
                )
            )
    }

    @PostMapping("/votes")
    fun getVotes(
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<Map<String, List<VoteDto2>>> {
        val validationResult = authService.validateToken(token)
        if (validationResult !is TokenValidationResult.Success) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity.ok().body(votesService.findAll())
    }

    @GetMapping("/vote")
    fun getVote(
        @RequestParam title: String,
        @RequestParam token: String
    ): ResponseEntity<List<VoteDto2>> {
        val validationResult = authService.validateToken(token)
        if (validationResult !is TokenValidationResult.Success) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity.ok().body(votesService.findByTitle(title))
    }
}