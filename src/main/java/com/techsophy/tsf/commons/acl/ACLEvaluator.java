package com.techsophy.tsf.commons.acl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ACLEvaluator
{
    private final String gatewayURL;
    private final WebClient webClient;

    public ACLEvaluator(String gatewayURL)
    {
        this.gatewayURL=gatewayURL;
        this.webClient=WebClient.builder().build();          //creates only single instance of webClient
    }

    private ACLValidate evaluateACL(Map<String,?> context,String aclId,String token)
    {
        var contextMap= Collections.singletonMap("context",new HashMap<>());
        var result= webClient
                .post()
                .uri(gatewayURL+Constants.getACCOUNTS_ACL_URL() +aclId+"/evaluate")
                .headers(header -> header.setBearerAuth(token))
                .headers(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .bodyValue(contextMap)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBaseModel<ACLValidate>>() {})
                .blockOptional()
                .orElseThrow();
        return result.getData();
    }

    public ACLDecision getRead(Map<String,?> context,String aclId,String token)
    {
      return   evaluateACL(context,aclId,token).getRead();
    }

    public ACLDecision getUpdate(Map<String,?> context,String aclId,String token)
    {
        return   evaluateACL(context,aclId,token).getUpdate();
    }

    public ACLDecision getDelete(Map<String,?> context,String aclId,String token)
    {
        return   evaluateACL(context,aclId,token).getDelete();
    }
}
