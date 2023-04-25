package com.techsophy.tsf.commons.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.commons.config.GlobalMessageSource;
import com.techsophy.tsf.commons.exception.ExternalServiceErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Objects;
import static com.techsophy.tsf.commons.constants.CommonConstants.*;
import static com.techsophy.tsf.commons.constants.ErrorConstants.SERVICE_NOT_AVAILABLE;


@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WebClientWrapper {
    private final GlobalMessageSource globalMessageSource;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @CircuitBreaker(name = SERVICE, fallbackMethod = "availableMethod")
    public String webclientRequest(String url, @NotBlank String requestType, Object data)
    {
        if (requestType.equalsIgnoreCase(GET)) {
            return Objects.requireNonNull(webClient.get()
                            .uri(url)
                            .retrieve()
                            .onStatus(HttpStatus::isError, clientResponse -> {
                                Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                return errorMessage.flatMap(msg -> {
                                    throw new ExternalServiceErrorException(msg, msg);
                                });
                            }))
                    .bodyToMono(String.class)
                    .block();
        } else {
            if (requestType.equalsIgnoreCase(DELETE)) {
                if (data == null) {
                    return Objects.requireNonNull(webClient.method(HttpMethod.DELETE)
                                    .uri(url)
                                    .retrieve()
                                    .onStatus(HttpStatus::isError, clientResponse -> {
                                        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                        return errorMessage.flatMap(msg -> {
                                            throw new ExternalServiceErrorException(msg, msg);
                                        });
                                    }))
                            .bodyToMono(String.class)
                            .block();
                } else {
                    return Objects.requireNonNull(webClient.method(HttpMethod.DELETE)
                                    .uri(url)
                                    .bodyValue(data)
                                    .retrieve()
                                    .onStatus(HttpStatus::isError, clientResponse -> {
                                        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                        return errorMessage.flatMap(msg -> {
                                            throw new ExternalServiceErrorException(msg, msg);
                                        });
                                    }))
                            .bodyToMono(String.class)
                            .block();
                }
            }
            if (requestType.equalsIgnoreCase(PUT)) {
                return Objects.requireNonNull(webClient.put()
                                .uri(url)
                                .bodyValue(data)
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> {
                                    Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                    return errorMessage.flatMap(msg -> {
                                        throw new ExternalServiceErrorException(msg, msg);
                                    });
                                }))
                        .bodyToMono(String.class)
                        .block();
            }
            return Objects.requireNonNull(webClient.post()
                            .uri(url)
                            .bodyValue(data)
                            .retrieve()
                            .onStatus(HttpStatus::isError, clientResponse -> {
                                Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                return errorMessage.flatMap(msg -> {
                                    throw new ExternalServiceErrorException(msg, msg);
                                });
                            }))
                    .bodyToMono(String.class)
                    .block();
        }
    }

    public String availableMethod(Exception ex) throws JsonProcessingException
    {
        String abc = ex.getMessage();
        Map<String, Object> map = this.objectMapper.readValue(abc, Map.class);
        throw new ExternalServiceErrorException(SERVICE_NOT_AVAILABLE, globalMessageSource.get(SERVICE_NOT_AVAILABLE, String.valueOf(map.get("path"))));
    }
}

