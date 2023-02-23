package com.awgment.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;
import static com.awgment.commons.Constants.*;

@AllArgsConstructor
public class ACLEvaluator
{
    private String aclId;
    private Map<String,?> context;

    private ACLValidate evaluateACL(String token) throws JsonProcessingException
    {
        WebClient webClient= WebClient
                .builder()
                .defaultHeader(AUTHORIZATION, BEARER + token)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        Map<String,Object> contextMap=new HashMap<>();
        contextMap.put("context",new HashMap<>());
        String result= webClient
                .post()
                .uri(System.getenv("gateway.uri")+ACCOUNTS_ACL_URL +aclId+"/evaluate")
                .bodyValue(contextMap)
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional().orElseThrow();
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> map=objectMapper.readValue(result,Map.class);
        return objectMapper.convertValue(map.get("data"),ACLValidate.class);
    }

    public ACLDecision getRead(String token) throws JsonProcessingException
    {
      return   evaluateACL(token).getRead();
    }

    public ACLDecision getUpdate(String token) throws JsonProcessingException
    {
        return   evaluateACL(token).getUpdate();
    }

    public ACLDecision getDelete(String token) throws JsonProcessingException
    {
        return   evaluateACL(token).getRead();
    }
}
