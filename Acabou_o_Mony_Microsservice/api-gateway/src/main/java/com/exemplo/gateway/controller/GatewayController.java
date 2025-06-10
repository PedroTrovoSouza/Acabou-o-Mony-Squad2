package com.exemplo.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final WebClient webClient;

    public GatewayController() {
        this.webClient = WebClient.builder().build();
    }

    @RequestMapping("/{service}/{path:^(?!api).*$}/**")
    public Mono<ResponseEntity<String>> proxy(
            @PathVariable String service,
            @PathVariable String path,
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) MultiValueMap<String, String> queryParams,
            @RequestBody(required = false) Mono<String> body,
            ServerHttpRequest request) {

        String baseUrl = switch (service) {
            case "pedidos-service" -> "http://localhost:9090";
            case "produtos-service" -> "http://localhost:9091";
            case "contas-service" -> "http://localhost:9092";
            case "transacao-service" -> "http://localhost:9093";
            default -> null;
        };

        if (baseUrl == null) return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serviço não encontrado"));

        String fullPath = request.getURI().getRawPath().replace("/api/" + service, "");

        return webClient.method(request.getMethod())
                .uri(baseUrl + fullPath)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(body == null ? Mono.empty() : body, String.class)
                .retrieve()
                .toEntity(String.class);
    }
}
