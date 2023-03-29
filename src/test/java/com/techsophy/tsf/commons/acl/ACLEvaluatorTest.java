package com.techsophy.tsf.commons.acl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getALLOW(),null);
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
        Assertions.assertEquals(ACLConstants.getALLOW(),aclEvaluator.getRead(aclId, token, context).getDecision());
    }

    @Test
    void getRead_ReturnsWithoutTokenExpectedACLDecisionTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getALLOW(),null);
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
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        Assertions.assertEquals(ACLConstants.getALLOW(),aclEvaluator.getRead(aclId,context).getDecision());
    }

    @Test
    void getUpdate_ReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getDENY(),null);
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
        Assertions.assertEquals(ACLConstants.getDENY(),aclEvaluator.getUpdate(aclId, token, context).getDecision());
    }

    @Test
    void getUpdate_WithoutTokenReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getDENY(),null);
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
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        Assertions.assertEquals(ACLConstants.getDENY(),aclEvaluator.getUpdate(aclId, context).getDecision());
    }

    @Test
    void getDelete_ReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getALLOW(),null);
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
        Assertions.assertEquals(ACLConstants.getALLOW(),aclEvaluator.getDelete(aclId, token, context).getDecision());
    }

    @Test
    void getDelete_WithoutTokenReturnsExpectedResultTest()
    {
        Map<String,?> context=new HashMap<>();
        ACLEvaluatorImpl aclEvaluator=new ACLEvaluatorImpl(gatewayURL);
        ACLDecision aclDecision=new ACLDecision(ACLConstants.getALLOW(),null);
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
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        Assertions.assertEquals(ACLConstants.getALLOW(),aclEvaluator.getDelete(aclId, context).getDecision());
    }
}
