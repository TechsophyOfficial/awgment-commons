package com.techsophy.tsf.commons;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;
import static com.techsophy.tsf.commons.Constants.ACCOUNTS_ACL_URL;

@AllArgsConstructor
public class ACLEvaluator
{
    private String aclId;
    private Map<String,?> context;
    private String gatewayURL;

    private ACLValidate evaluateACL(String token)
    {
        WebClient webClient= WebClient
                .builder()
                .build();
        Map<String,Object> contextMap=new HashMap<>();
        contextMap.put("context",new HashMap<>());
        ResponseBaseModel<ACLValidate> result= webClient
                .post()
                .uri(gatewayURL+ACCOUNTS_ACL_URL +aclId+"/evaluate")
                .headers(header -> header.setBearerAuth(token))
                .headers(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .bodyValue(contextMap)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBaseModel<ACLValidate>>() {})
                .blockOptional().orElseThrow();
        return result.getData();
    }

    public ACLDecision getRead(String token)
    {
      return   evaluateACL(token).getRead();
    }

    public ACLDecision getUpdate(String token)
    {
        return   evaluateACL(token).getUpdate();
    }

    public ACLDecision getDelete(String token)
    {
        return   evaluateACL(token).getDelete();
    }
}
