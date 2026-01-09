package ru.wolfram.administration.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import ru.wolfram.administration.model.Vote

@Service
class AdministrationServiceImpl(
    private val restTemplate: RestTemplate,
    @Value($$"${write_votes.baseUrl}") private val writeVotesBaseUrl: String
) : AdministrationService {
    override fun getVoteOffer(): List<Vote>? {
        val url = UriComponentsBuilder
            .fromUriString("${writeVotesBaseUrl}/get-random-vote")
            .toUriString()
        return restTemplate.getForObject<Array<Vote>>(url, Array<Vote>::class)?.toList()
    }

    override fun accept(title: String) {
        val url = UriComponentsBuilder
            .fromUriString("${writeVotesBaseUrl}/accept-vote")
            .queryParam("title", title)
            .toUriString()
        restTemplate.postForObject(url, null, Void::class.java)
    }

    override fun reject(title: String) {
        val url = UriComponentsBuilder
            .fromUriString("${writeVotesBaseUrl}/reject-vote")
            .queryParam("title", title)
            .toUriString()
        restTemplate.postForObject(url, null, Void::class.java)
    }

}