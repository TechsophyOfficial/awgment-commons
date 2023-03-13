package com.techsophy.tsf.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

class TokenUtilsTest
{
    @Test
    void getTokenFromContextWithOauth2UserTest()
    {
        OAuth2User mockOAuth2User= Mockito.mock(OAuth2User.class);
        Mockito.when(mockOAuth2User.getName()).thenReturn("test-name");
        Authentication mockAuthentication=Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockOAuth2User);
        SecurityContext mockSecurityContext=Mockito.mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        TokenUtils tokenUtils=new TokenUtils();
        Assertions.assertEquals("test-name",tokenUtils.getTokenFromContext());
    }

    @Test
    void getTokenFromContextWithJwtTest()
    {
        Jwt mockJwt=Mockito.mock(Jwt.class);
        Mockito.when(mockJwt.getTokenValue()).thenReturn("test-token");
        Authentication mockAuthentication=Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockJwt);
        SecurityContext mockSecurityContext=Mockito.mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        TokenUtils tokenUtils=new TokenUtils();
        Assertions.assertEquals("test-token",tokenUtils.getTokenFromContext());
    }

    @Test
    void getTokenFromContextNullAuthenticationTest()
    {
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(mockSecurityContext);
        TokenUtils tokenUtils=new TokenUtils();
        Assertions.assertThrows(SecurityException.class, tokenUtils::getTokenFromContext);
    }

    @Test
    void getTokenFromContextWithAnonymousTokenTest()
    {
        AnonymousAuthenticationToken mockAnonymousAuthenticationToken = Mockito.mock(AnonymousAuthenticationToken.class);
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockAnonymousAuthenticationToken);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        TokenUtils tokenUtils = new TokenUtils();
        Assertions.assertThrows(SecurityException.class, tokenUtils::getTokenFromContext);
    }
}
