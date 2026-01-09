package ru.wolfram.write_votes.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.write_votes.dto.VoteDto
import ru.wolfram.write_votes.service.VotesService

@RestController
@RequestMapping("/api/v1/write-votes")
class VotesController(
    private val votesService: VotesService
) {
    @PostMapping("/create-vote")
    suspend fun createVote(
        @RequestBody votesDto: List<VoteDto>,
    ): ResponseEntity<Unit> {
        require(votesService.create(votesDto) > 0)
        return ResponseEntity.ok()
            .body(Unit)
    }

    @GetMapping("/get-random-vote")
    suspend fun getRandomVote(): ResponseEntity<List<VoteDto>> {
        return ResponseEntity.ok().body(votesService.getRandomVote())
    }

    @PostMapping("/accept-vote")
    suspend fun acceptVote(
        @RequestParam(value = "title") title: String
    ) {
        votesService.acceptVote(title)
    }

    @PostMapping("/reject-vote")
    suspend fun rejectVote(
        @RequestParam(value = "title") title: String
    ) {
        votesService.rejectVote(title)
    }
}