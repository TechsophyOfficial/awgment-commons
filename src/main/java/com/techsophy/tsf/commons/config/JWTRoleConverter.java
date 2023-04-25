package com.techsophy.tsf.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.commons.user.TokenUtils;
import com.techsophy.tsf.commons.user.WebClientWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.techsophy.tsf.commons.constants.CommonConstants.*;
import static com.techsophy.tsf.commons.constants.ErrorConstants.TOKEN_VERIFICATION_FAILED;


@Slf4j

@AllArgsConstructor(onConstructor_ = {@Autowired})
public class JWTRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    private final WebClientWrapper webClientWrapper;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;
    private final String keyCloakApi;
    private static final Logger logger = LoggerFactory.getLogger(JWTRoleConverter.class);

    @SneakyThrows
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt)
    {
        List<String> totalList;
        List<String> awgmentRolesList=new ArrayList<>();
        String token= jwt.getTokenValue();
        String userInfoResponse = webClientWrapper.webclientRequest(keyCloakApi+tokenUtils.getIssuerFromToken(jwt.getTokenValue())+USER_INFO_URL,GET,null);
        if(userInfoResponse.isEmpty())
        {
            logger.info(TOKEN_VERIFICATION_FAILED);
            throw new AccessDeniedException(TOKEN_VERIFICATION_FAILED);
        }
        Map<String,Object> userInformationMap=this.objectMapper.readValue(userInfoResponse,Map.class);
        if(userInformationMap.containsKey(CLIENT_ROLES))
        {
            totalList = this.objectMapper.convertValue(userInformationMap.get(CLIENT_ROLES), List.class);
            awgmentRolesList.addAll(totalList);
            if(awgmentRolesList.isEmpty())
            {
                logger.info(AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES);
            }
        }
        else
        {
            logger.info(CLIENT_ROLES_MISSING_IN_USER_INFORMATION);
        }
        return (awgmentRolesList).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}