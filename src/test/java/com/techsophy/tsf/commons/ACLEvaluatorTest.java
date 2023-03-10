package com.techsophy.tsf.commons;

import com.techsophy.tsf.commons.acl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class})
class ACLEvaluatorTest
{
    @Mock
    private WebClient webClient;
    private final String gatewayURL="http://localhost:8080";
    private final String aclId="101";
    private final String token="testToken";

    @Test
    void getRead_ReturnsExpectedACLDecisionTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluator aclEvaluator=new ACLEvaluator(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(Constants.getALLOW(),null);
        ACLValidate aclValidate=new ACLValidate("rule1",aclDecision,null,null);
        when(webClient.post()).thenReturn(Mockito.mock(WebClient.RequestBodyUriSpec.class));
        when(webClient.post().uri(anyString())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any())).thenReturn(Mockito.mock(WebClient.RequestHeadersSpec.class));
        WebClient.ResponseSpec responseSpec=Mockito.mock(WebClient.ResponseSpec.class);
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any()).retrieve()).thenReturn(responseSpec);
        ResponseBaseModel responseBaseModel=new ResponseBaseModel();
        responseBaseModel.setData(aclValidate);
        responseBaseModel.setSuccess(true);
        responseBaseModel.setMessage("acl validate is returned");
        when(responseSpec.bodyToMono((ParameterizedTypeReference<Object>) any())).thenReturn(Mono.just(responseBaseModel));
        ReflectionTestUtils.setField(aclEvaluator,"webClient",webClient);
        Assertions.assertEquals(Constants.getALLOW(),aclEvaluator.getRead(context,aclId,token).getDecision());
    }

    @Test
    void getUpdate_ReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluator aclEvaluator=new ACLEvaluator(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(Constants.getDENY(),null);
        ACLValidate aclValidate=new ACLValidate("rule1",null,aclDecision,null);
        when(webClient.post()).thenReturn(Mockito.mock(WebClient.RequestBodyUriSpec.class));
        when(webClient.post().uri(anyString())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any())).thenReturn(Mockito.mock(WebClient.RequestHeadersSpec.class));
        WebClient.ResponseSpec responseSpec=Mockito.mock(WebClient.ResponseSpec.class);
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any()).retrieve()).thenReturn(responseSpec);
        ResponseBaseModel responseBaseModel=new ResponseBaseModel();
        responseBaseModel.setData(aclValidate);
        responseBaseModel.setSuccess(true);
        responseBaseModel.setMessage("acl validate is returned");
        when(responseSpec.bodyToMono((ParameterizedTypeReference<Object>) any())).thenReturn(Mono.just(responseBaseModel));
        ReflectionTestUtils.setField(aclEvaluator,"webClient",webClient);
        Assertions.assertEquals(Constants.getDENY(),aclEvaluator.getUpdate(context,aclId,token).getDecision());
    }

    @Test
    void getDelete_ReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluator aclEvaluator=new ACLEvaluator(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(Constants.getALLOW(),null);
        ACLValidate aclValidate=new ACLValidate("rule3",null,null,aclDecision);
        when(webClient.post()).thenReturn(Mockito.mock(WebClient.RequestBodyUriSpec.class));
        when(webClient.post().uri(anyString())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any())).thenReturn(Mockito.mock(WebClient.RequestBodySpec.class));
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any())).thenReturn(Mockito.mock(WebClient.RequestHeadersSpec.class));
        WebClient.ResponseSpec responseSpec=Mockito.mock(WebClient.ResponseSpec.class);
        when(webClient.post().uri(anyString()).headers(any()).headers(any()).bodyValue(any()).retrieve()).thenReturn(responseSpec);
        ResponseBaseModel responseBaseModel=new ResponseBaseModel();
        responseBaseModel.setData(aclValidate);
        responseBaseModel.setSuccess(true);
        responseBaseModel.setMessage("acl validate is returned");
        when(responseSpec.bodyToMono((ParameterizedTypeReference<Object>) any())).thenReturn(Mono.just(responseBaseModel));
        ReflectionTestUtils.setField(aclEvaluator,"webClient",webClient);
        Assertions.assertEquals(Constants.getALLOW(),aclEvaluator.getDelete(context,aclId,token).getDecision());
    }
}
