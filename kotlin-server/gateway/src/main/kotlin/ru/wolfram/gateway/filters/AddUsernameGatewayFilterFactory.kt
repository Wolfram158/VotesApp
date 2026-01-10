package ru.wolfram.gateway.filters

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import ru.wolfram.gateway.constants.Constants
import ru.wolfram.gateway.jwt.JwtValidator

@Component
@Order(2)
class AddUsernameGatewayFilterFactory(
    private val jwtValidator: JwtValidator
) : AbstractGatewayFilterFactory<Config>(Config::class.java) {
    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val authorizationHeader = exchange.request.headers.getFirst(Constants.AUTHORIZATION_HEADER_KEY)
            val token = authorizationHeader?.substring(Constants.BEARER_PREFIX.length)
            val username = jwtValidator.extractAllClaims(token).subject
            if (username == null) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter exchange.response.setComplete()
            }
            val originalRequest = exchange.request
            val originalQueryParams = originalRequest.queryParams
            val mutatedQueryParams = LinkedMultiValueMap(originalQueryParams)
            mutatedQueryParams.remove("username")
            mutatedQueryParams.add("username", username)
            val mutatedRequest = originalRequest.mutate()
                .uri(
                    UriComponentsBuilder
                        .fromUriString(originalRequest.uri.toString())
                        .replaceQueryParams(mutatedQueryParams)
                        .build()
                        .toUri()
                )
                .build()
            val mutatedExchange = exchange.mutate().request(mutatedRequest).build()
            chain.filter(mutatedExchange)
        }
    }

}