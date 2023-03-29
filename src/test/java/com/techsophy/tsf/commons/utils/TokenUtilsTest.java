package com.techsophy.tsf.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.commons.acl.ResponseBaseModel;
import com.techsophy.tsf.commons.user.UserDetails;
import com.techsophy.tsf.commons.user.UserFormDataDefinition;
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
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TokenUtilsTest
{
    @Mock
    private WebClient webClient;
    private final String gatewayURL="http://localhost:8080";

    @Test
    void getTokenFromContextWithOauth2UserTest()
    {
        OAuth2User mockOAuth2User= mock(OAuth2User.class);
        Mockito.when(mockOAuth2User.getName()).thenReturn("test-name");
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockOAuth2User);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertEquals("test-name",tokenUtils.getToken().orElseThrow());
    }

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
    void getTokenFromContextNullAuthenticationTest()
    {
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertThrows(SecurityException.class, tokenUtils::getToken);
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
    void getIssuerOAuth2Test()
    {
        OAuth2User mockOAuth2User= mock(OAuth2User.class);
        Mockito.when(mockOAuth2User.getName()).thenReturn(gatewayURL);
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockOAuth2User);
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
    void getUserIdJwtTest() throws JsonProcessingException
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
        Mockito.when(mockJwt.getTokenValue()).thenReturn(null);
        Authentication mockAuthentication= mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext= mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        UserDetails tokenUtils=new UserDetails(gatewayURL);
        Assertions.assertThrows(NoSuchElementException.class,()->tokenUtils.getUserId().orElseThrow());
    }

    @Test
    void getUserIdJwtUserDetailsTest() throws JsonProcessingException
    {
        Jwt mockJwt= mock(Jwt.class);
        Map<String,Object> claims=new HashMap<>();
        Mockito.when(mockJwt.getClaims()).thenReturn(claims);
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
        when(responseSpec.bodyToMono(eq(String.class))).thenReturn(Mono.just(expectedResponse));
        ResponseBaseModel responseBaseModel=new ResponseBaseModel();
        UserFormDataDefinition userFormDataDefinition=new UserFormDataDefinition();
        userFormDataDefinition.setUserId("101");
        responseBaseModel.setData(userFormDataDefinition);
        responseBaseModel.setSuccess(true);
        responseBaseModel.setMessage("Logged In User details fetched successfully");
        ReflectionTestUtils.setField(userDetails,"webClient",webClient);
        Assertions.assertEquals("1075351150762643432", userDetails.getUserId().orElseThrow());
    }
}
