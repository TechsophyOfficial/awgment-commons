package com.techsophy.tsf.commons.acl;

import com.techsophy.tsf.commons.user.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ACLEvaluatorImpl  implements ACLEvaluation
{
    private final String gatewayURL;
    private final WebClient webClient;
    private final UserDetails tokenUtils;

    public ACLEvaluatorImpl(String gatewayURL)
    {
        this.gatewayURL=gatewayURL;
        this.webClient=WebClient.builder().build(); //creates only single instance of webClient
        this.tokenUtils=new UserDetails(gatewayURL);
    }

    public ACLValidate evaluateACL(String aclId,String token,Map<String,?> context)
    {
        var contextMap= Collections.singletonMap("context",new HashMap<>());
        var result= webClient
                .post()
                .uri(gatewayURL+ ACLConstants.getACCOUNTS_ACL_URL() +aclId+"/evaluate")
                .headers(header -> header.setBearerAuth(token))
                .headers(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .bodyValue(contextMap)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBaseModel<ACLValidate>>() {})
                .blockOptional()
                .orElseThrow();
        return result.getData();
    }

    public ACLDecision getRead(String aclId,Map<String,?> context)
    {
        return   evaluateACL(aclId,tokenUtils.getToken().orElseThrow(), context).getRead();
    }

    public ACLDecision getRead(String aclId,String token,Map<String,?> context)
    {
        return   evaluateACL(aclId, token, context).getRead();
    }

    public ACLDecision getUpdate(String aclId,Map<String,?> context)
    {
        return   evaluateACL(aclId, tokenUtils.getToken().orElseThrow(), context).getUpdate();
    }

    public ACLDecision getUpdate(String aclId,String token,Map<String,?> context)
    {
        return   evaluateACL(aclId, token, context).getUpdate();
    }

    public ACLDecision getDelete(String aclId,Map<String,?> context)
    {
        return   evaluateACL(aclId,tokenUtils.getToken().orElseThrow(), context).getDelete();
    }

    public ACLDecision getDelete(String aclId,String token,Map<String,?> context)
    {
        return   evaluateACL(aclId, token, context).getDelete();
    }
}
