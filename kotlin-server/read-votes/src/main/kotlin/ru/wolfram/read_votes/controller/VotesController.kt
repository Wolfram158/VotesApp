package ru.wolfram.read_votes.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.read_votes.dto.TitleDto
import ru.wolfram.read_votes.dto.TitlesDto
import ru.wolfram.read_votes.dto.VoteDto
import ru.wolfram.read_votes.dto.VoteDto2
import ru.wolfram.read_votes.service.VotesService

@RestController
@RequestMapping("/api/v1/read-votes")
class VotesController(
    private val votesService: VotesService
) {
    @PostMapping("/do-vote")
    suspend fun vote(
        @RequestBody voteDto: VoteDto,
        @RequestParam("username") username: String
    ): ResponseEntity<List<VoteDto2>> {
        return ResponseEntity.ok()
            .body(
                votesService.incrementVotesCount(
                    username = username,
                    title = voteDto.title,
                    variant = voteDto.variant
                )
            )
    }

    @GetMapping("/votes")
    suspend fun getVotes(
        @RequestParam("page") page: Int
    ): ResponseEntity<TitlesDto> {
        return ResponseEntity.ok().body(
            TitlesDto(votesService.findAllInRange(10, (page - 1) * 10))
        )
    }

    @PostMapping("/vote")
    suspend fun getVote(
        @RequestBody titleDto: TitleDto,
    ): ResponseEntity<List<VoteDto2>> {
        return ResponseEntity.ok().body(votesService.findByTitle(titleDto))
    }
}