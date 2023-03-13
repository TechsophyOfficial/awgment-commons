package com.techsophy.tsf.commons.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.Optional;
import static com.techsophy.tsf.commons.utils.UtilsConstants.UNABLE_TO_FETCH_TOKEN;

public class TokenUtils
{
    public String getTokenFromContext()
    {
            var securityContext = SecurityContextHolder.getContext();
            var authentication=securityContext.getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            {
                throw new SecurityException(UNABLE_TO_FETCH_TOKEN);
            }
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User)
            {
                return Optional.of(((OAuth2User) principal).getName()).orElseThrow(SecurityException::new);
            }
            else if (principal instanceof Jwt)
            {
                Jwt jwt = (Jwt) principal;
                return jwt.getTokenValue();
            }
            else
            {
                throw new SecurityException(UNABLE_TO_FETCH_TOKEN);
            }
    }
}
