package com.techsophy.tsf.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.techsophy.tsf.commons.config.GlobalMessageSource;
import com.techsophy.tsf.commons.user.WebClientWrapper;
import lombok.Data;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TestSuite {
    WireMockServer wireMockServer ;
    WebClient webClient;
    GlobalMessageSource globalMessageSource;
    ObjectMapper objectMapper;
    WebClientWrapper webClientWrapper1;

    @BeforeAll
    public void beforeTest() {
        webClient = WebClient.create("http://localhost:9292");
        webClientWrapper1 =new WebClientWrapper(globalMessageSource,objectMapper,webClient);
        wireMockServer = new WireMockServer(9292);
        wireMockServer.start();
        WireMock.configureFor("localhost",wireMockServer.port());
    }


    public void commonStubs() {
        stubFor(get("/v1/notification").willReturn(aResponse()
                .withStatus(404)
                .withBody("{\"status\":\"Error\",\"message\":\"Endpoint not found\"}"))
        );
        stubFor(get("/v1/notification/file").willReturn(aResponse()
                .withStatus(400)
                .withBodyFile("resources/testdata/token.txt"))
        );
        stubFor(delete("/v1/notification/file").willReturn(aResponse()
                .withStatus(400)
                .withBodyFile("resources/testdata/token.txt"))
        );
        stubFor(delete("/v1/notification/file").willReturn(aResponse()
                .withStatus(404)
                .withBodyFile("resources/testdata/token.txt"))
        );
        stubFor(put("/v1/notification/file").willReturn(aResponse()
                .withStatus(400)
                .withBodyFile("resources/testdata/token.txt"))
        );
        stubFor(put("/v1/notification/file").willReturn(aResponse()
                .withStatus(404)
                .withBodyFile("resources/testdata/token.txt"))
        );
        stubFor(get(urlPathEqualTo("/v1/users")).willReturn(aResponse()
                .withBody("{\"username\":\"techsophy\",\"message\":\"user retrieved successfully\"}"))
        );

        stubFor(post("/v1/notification").willReturn(aResponse()
                .withStatus(404)
                .withBody("{\"status\":\"Error\",\"message\":\"Endpoint not found\"}"))
        );
        UrlPattern externalUrl = urlPathMatching("/v2/users");
        stubFor(get("/v2/users").willReturn(ok("{\n" +
                "    \"intent\": {\n" +
                "        \"name\": \"notificationJourney\",\n" +
                "        \"confidence\": 1.0\n" +
                "    },\n" +
                "    \"entities\": [],\n" +
                "    \"text\": \"hi\",\n" +
                "    \"project\": \"default\",\n" +
                "    \"model\": \"fallback\"\n" +
                "}")
                .withHeader("Content-Type", "application/json").withStatus(200)));

    }


    @Data
    private static class Response {
        private String result;
    }
    @AfterAll
    public void afterTest() {
        wireMockServer.findNearMissesForAllUnmatchedRequests().stream().forEach(System.out::println);
        wireMockServer.stop();
        wireMockServer.resetAll();
    }

}