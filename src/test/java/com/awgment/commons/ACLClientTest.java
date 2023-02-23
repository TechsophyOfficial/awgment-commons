package com.awgment.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import static com.awgment.commons.TestConstants.DENY;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ACLClientTest
{
    WireMockServer wireMockServer ;
    WebClient webClient;
    ObjectMapper objectMapper;
    @InjectMocks
    ACLEvaluator aclEvaluator=new ACLEvaluator("101",null);

    @BeforeAll
    public void beforeTest()
    {
        wireMockServer = new WireMockServer(8080); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();
        wireMockServer.resetAll();
        WireMock.configureFor("localhost", 8080);
        commonStubs();
    }

    public void commonStubs()
    {
        stubFor(post("/accounts/v1/acl/101/evaluate").willReturn(okJson("{\n" +
                "    \"data\": {\n" +
                "        \"name\": \"aclRule\",\n" +
                "        \"read\": {\n" +
                "            \"decision\": \"deny\",\n" +
                "            \"additionalDetails\": null\n" +
                "        },\n" +
                "        \"update\": {\n" +
                "            \"decision\": \"undefined\",\n" +
                "            \"additionalDetails\": null\n" +
                "        },\n" +
                "        \"delete\": {\n" +
                "            \"decision\": \"deny\",\n" +
                "            \"additionalDetails\": null\n" +
                "        }\n" +
                "    },\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"ACL evaluated successfully\"\n" +
                "}").withStatus(200)));
    }

    @Test
    void getReadAllowTest() throws JsonProcessingException
    {
        ACLDecision aclDecision=new ACLDecision();
        aclDecision.setDecision(DENY);
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getRead("token"));
        aclDecision.setDecision("undefined");
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getUpdate("token"));
        aclDecision.setDecision(DENY);
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getDelete("token"));
    }
}
