package com.techsophy.tsf.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.commons.acl.ResponseBaseModel;
import com.techsophy.tsf.commons.user.UserData;
import com.techsophy.tsf.commons.user.UserDetails;
import com.techsophy.tsf.commons.user.UserFormDataDefinition;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UserDetailsTest
{
    @Mock
    private WebClient webClient;
    private final String gatewayURL="http://localhost:8082";

    @Test
    void getTokenFromContextWithJwtTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("test-token",tokenUtils.getToken().orElseThrow());
    }

    @Test
    void getTokenFromContextSecurityExceptionWithJwtTest()
    {
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals(Optional.empty(),tokenUtils.getToken());
    }

    @Test
    void getIssuerJwtTest() throws MalformedURLException
    {
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getIssuer()).thenReturn(new URL(gatewayURL));
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals(gatewayURL,tokenUtils.getIssuer().orElseThrow());
    }

    @Test
    void getTokenUserNameOAuth2Test()
    {
        OAuth2User mockOAuth2User= mock(OAuth2User.class);
        Mockito.when(mockOAuth2User.getName()).thenReturn("test-name");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockOAuth2User);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("test-name",tokenUtils.getTokenUsername().orElseThrow());
    }

    @Test
    void getTokenUserNameJwtTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Mockito.when(mockJwt.getClaim(Mockito.any())).thenReturn("test-user-name");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("test-user-name",tokenUtils.getTokenUsername().orElseThrow());
    }

    @Test
    void getUserIdJwtTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId","101");
        Mockito.when(mockJwt.getClaims()).thenReturn(claims);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("101",tokenUtils.getUserId().orElseThrow());
    }

    @Test
    void getUserIdTokenEmptyTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId","101");
//        Mockito.when(mockJwt.getTokenValue()).thenReturn(null);
        Authentication mockAuthentication= mock(Authentication.class);
//        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
//        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("101",tokenUtils.getUserId().orElseThrow());
    }

    @Test
    void getUserIdJwtUserDetailsTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
//        Mockito.when(mockJwt.getClaims()).thenReturn(claims);
//        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
//        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
//        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails userDetails =new UserDetails(gatewayURL);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
//        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
//        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
//        when(requestHeadersSpecMock.header(any(), anyString())).thenReturn(requestHeadersSpecMock);
//        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        WebClient.ResponseSpec responseSpec= mock(WebClient.ResponseSpec.class);
//        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpec);
        String expectedResponse="{\n" +
                "    \"data\": {\n" +
                "        \"userData\": {\n" +
                "            \"userName\": \"mohanakhil\",\n" +
                "            \"firstName\": \"mohanakhil\",\n" +
                "            \"lastName\": \"denduluri\",\n" +
                "            \"mobileNumber\": \"(123) 456-7899\",\n" +
                "            \"emailId\": \"mohanakhil.d@techsophy.com\",\n" +
                "            \"department\": \"developer\",\n" +
                "            \"groups\": [\n" +
                "                \"DevOps\",\n" +
                "                \"camunda-admin\"\n" +
                "            ],\n" +
                "            \"roles\": []\n" +
                "        },\n" +
                "        \"userId\": \"1075351150762643432\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"createdById\": \"867402957087965184\",\n" +
                "        \"createdOn\": \"2023-02-15T09:41:33.394Z\",\n" +
                "        \"updatedById\": \"867402957087965184\",\n" +
                "        \"updatedOn\": \"2023-02-15T09:41:33.407Z\"\n" +
                "    },\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"Logged In User details fetched successfully\"\n" +
                "}";
//        when(responseSpec.bodyToMono(eq(String.class))).thenReturn(Mono.just(expectedResponse));
        ResponseBaseModel responseBaseModel=new ResponseBaseModel();
        UserFormDataDefinition userFormDataDefinition=new UserFormDataDefinition();
        userFormDataDefinition.setUserId("101");
        responseBaseModel.setData(userFormDataDefinition);
        responseBaseModel.setSuccess(true);
        responseBaseModel.setMessage("Logged In User details fetched successfully");
        ReflectionTestUtils.setField(userDetails,"webClient",webClient);
        Assertions.assertEquals("101", userDetails.getUserId().orElseThrow());
    }

    @Test
    void getUserIdJwtUserDetailsWebclientExceptionTest()
    {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
//        Mockito.when(mockJwt.getClaims()).thenReturn(claims);
//        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
//        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
//        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails userDetails =new UserDetails(gatewayURL);
//        when(webClient.get()).thenThrow(WebClientResponseException.class);
        UserFormDataDefinition userFormDataDefinition=new UserFormDataDefinition();
        userFormDataDefinition.setUserId("101");
        ReflectionTestUtils.setField(userDetails,"webClient",webClient);
        Assertions.assertEquals("101",userDetails.getUserId().get());
    }

    @Test
    void getUserDetailsTest()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("userName","akhil");
        map.put("firstName","mohanakhil");
        map.put("lastName","denduluri");
        map.put("mobileNumber","9999999999");
        map.put("emailId","mohanakhil.d@techsophy.com");
        map.put("department","developer");
        map.put("groups",new ArrayList<>());
        map.put("roles",new ArrayList<>());
        UserFormDataDefinition userFormDataDefinition=new UserFormDataDefinition();
        userFormDataDefinition.setUserData(map);
        UserData userData=new UserData(map);
        Assertions.assertEquals(userData,userFormDataDefinition.getUserDetails());
    }
    @Test
    void getUserDetailsWrapperTest() throws JSONException, JsonProcessingException {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
//        Mockito.when(mockJwt.getClaims()).thenReturn(claims);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails userDetails =new UserDetails(gatewayURL);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.header(any(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        WebClient.ResponseSpec responseSpec= mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpec);
        String expectedResponse="{\n" +
                "    \"data\": {\n" +
                "        \"userData\": {\n" +
                "            \"userName\": \"mohanakhil\",\n" +
                "            \"firstName\": \"mohanakhil\",\n" +
                "            \"lastName\": \"denduluri\",\n" +
                "            \"mobileNumber\": \"(123) 456-7899\",\n" +
                "            \"emailId\": \"mohanakhil.d@techsophy.com\",\n" +
                "            \"department\": \"developer\",\n" +
                "            \"groups\": [\n" +
                "                \"DevOps\",\n" +
                "                \"camunda-admin\"\n" +
                "            ],\n" +
                "            \"roles\": []\n" +
                "        },\n" +
                "        \"userId\": \"1075351150762643432\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"createdById\": \"867402957087965184\",\n" +
                "        \"createdOn\": \"2023-02-15T09:41:33.394Z\",\n" +
                "        \"updatedById\": \"867402957087965184\",\n" +
                "        \"updatedOn\": \"2023-02-15T09:41:33.407Z\"\n" +
                "    },\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"Logged In User details fetched successfully\"\n" +
                "}";
        when(responseSpec.bodyToMono((String.class))).thenReturn(Mono.just(expectedResponse));
        JSONObject jsonObject = new JSONObject(expectedResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseBaseModel responseBaseModel = objectMapper.readValue(expectedResponse,ResponseBaseModel.class);
        UserFormDataDefinition userFormDataDefinition = new UserFormDataDefinition();
        userFormDataDefinition.setUserData((Map<String, Object>) responseBaseModel.getData());
        ReflectionTestUtils.setField(userDetails,"webClient",webClient);
        Assertions.assertEquals(userFormDataDefinition.getUserData().get("userData"),userDetails.getUserDetails().getUserData());
    }
}
