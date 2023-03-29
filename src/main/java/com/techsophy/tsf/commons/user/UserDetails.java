package com.techsophy.tsf.commons.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.commons.acl.ResponseBaseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.Optional;
import static com.techsophy.tsf.commons.user.UtilsConstants.*;

@RequiredArgsConstructor
public class UserDetails
{
    private final WebClient webClient;
    private final String gatewayURL;
    private final ObjectMapper objectMapper;

    public UserDetails(String gatewayURL)
    {
        this.webClient=WebClient.builder().build();
        this.gatewayURL=gatewayURL;
        this.objectMapper=new ObjectMapper();
    }

    private static Object getPrincipal()
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            throw new SecurityException(UNABLE_TO_FETCH_TOKEN);
        }
        return authentication.getPrincipal();
    }

    public Optional<String> getIssuer()
    {
        Object principal = getPrincipal();
        if (principal instanceof OAuth2User)
        {
            return Optional.ofNullable(((OAuth2User) principal).getName());
        }
        else
        {
            Jwt jwt = (Jwt) principal;
            return Optional.ofNullable(String.valueOf(jwt.getIssuer()));
        }
    }

    public Optional<String> getToken()
    {
        Object principal =getPrincipal();
        if (principal instanceof OAuth2User)
        {
            return Optional.ofNullable(((OAuth2User) principal).getName());
        }
        else
        {
            Jwt jwt = (Jwt) principal;
            return Optional.ofNullable(jwt.getTokenValue());
        }
    }

    public Optional<String> getTokenUsername()
    {
        Object principal = getPrincipal();
        if (principal instanceof OAuth2User)
        {
            return Optional.ofNullable(((OAuth2User) principal).getName());
        }
        else
        {
            Jwt jwt = (Jwt) principal;
            return Optional.ofNullable(jwt.getClaim(PREFERED_USERNAME));
        }
    }

    public Optional<String> getUserId() throws JsonProcessingException
    {
        if(getToken().isEmpty()) return Optional.empty();
        else
        {
            Object principal =getPrincipal();
            Jwt jwt = (Jwt) principal;
            Map<String,Object> claims=jwt.getClaims();
            if(claims.containsKey("userId"))
            {
                return Optional.ofNullable(String.valueOf(claims.get("userId")));
            }
            else
            {
                return Optional.ofNullable(getUserDetails().getUserId());
            }
        }
    }

    public UserFormDataDefinition getUserDetails() throws JsonProcessingException
    {
        String result= webClient
                .get()
                .uri(gatewayURL + ACCOUNT_URL + "/loggedIn")
                .header(HttpHeaders.AUTHORIZATION,getToken().orElseThrow())
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional()
                .orElseThrow();
        ResponseBaseModel<UserFormDataDefinition> responseBaseModel= objectMapper.readValue(result, ResponseBaseModel.class);
        return objectMapper.convertValue(responseBaseModel.getData(),UserFormDataDefinition.class);
    }
}