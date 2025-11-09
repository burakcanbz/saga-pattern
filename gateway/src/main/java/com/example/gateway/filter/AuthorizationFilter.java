package com.example.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public AuthorizationFilter() {
        super(Config.class);
    }

    public static class Config { }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = extractTokenFromHeader(request);
            if (token == null) {
                return sendErrorResponse(exchange, "Kimlik Token'ı Eksik", HttpStatus.UNAUTHORIZED);
            }

            try {
                // Token'ı parse et ve claims'i çıkar
                Claims claims = Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String userId = claims.getSubject();

                // İsteği zenginleştir (header'a userId ekle)
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-User-ID", userId)
                        .build();

                // İsteği yönlendir
                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (ExpiredJwtException e) {
                return sendErrorResponse(exchange, "Token Süresi Dolmuş", HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                return sendErrorResponse(exchange, "Token Geçersiz", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private String extractTokenFromHeader(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private Mono<Void> sendErrorResponse(ServerWebExchange exchange, String error, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        String errorBody = "{\"error\": \"" + error + "\"}";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorBody.getBytes())));
    }
}