package ru.wolfram.gateway.filters

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import ru.wolfram.gateway.constants.Constants
import ru.wolfram.gateway.jwt.JwtValidator

class Config

@Component
@Order(1)
class JwtAuthGatewayFilterFactory(
    private val jwtValidator: JwtValidator
) : AbstractGatewayFilterFactory<Config>(Config::class.java) {
    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val authorizationHeader = exchange.request.headers.getFirst(Constants.AUTHORIZATION_HEADER_KEY)
            if (authorizationHeader == null || !authorizationHeader.startsWith(Constants.BEARER_PREFIX)) {
                return@GatewayFilter handleUnauthorized(exchange)
            }
            val token = authorizationHeader.substring(Constants.BEARER_PREFIX.length)
            if (!jwtValidator.isJwtValid(token)) {
                return@GatewayFilter handleUnauthorized(exchange)
            }
            chain.filter(exchange)
        }
    }

    private fun handleUnauthorized(exchange: ServerWebExchange): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }

}