package com.techsophy.tsf.commons;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import static com.techsophy.tsf.commons.TestConstants.DENY;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ACLClientTest
{
    WireMockServer wireMockServer ;
    ACLEvaluator aclEvaluator;

    @BeforeAll
    public void beforeTest()
    {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.resetAll();
        commonStubs();
        aclEvaluator = new ACLEvaluator("101",null, wireMockServer.baseUrl());
    }

    @AfterAll
    public void teardown() {
        wireMockServer.shutdown();
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
    void getReadAllowTest()
    {
        ACLDecision aclDecision=new ACLDecision();
        aclDecision.setDecision(DENY);
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getRead("token"));
    }

    @Test
    void getUpdateUndefinedTest()
    {
        ACLDecision aclDecision=new ACLDecision();
        aclDecision.setDecision("undefined");
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getUpdate("token"));
    }

    @Test
    void getDeleteDenyTest()
    {
        ACLDecision aclDecision=new ACLDecision();
        aclDecision.setDecision(DENY);
        aclDecision.setAdditionalDetails(null);
        Assertions.assertEquals(aclDecision,aclEvaluator.getDelete("token"));
    }
}
