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
        Jwt jwt = (Jwt) principal;
        return Optional.ofNullable(String.valueOf(jwt.getIssuer()));
    }

    public Optional<String> getToken()
    {
        Object principal =getPrincipal();
        Jwt jwt = (Jwt) principal;
        return Optional.ofNullable(jwt.getTokenValue());
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
                .header(HttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2X2NzTUtiOVFsUVpETUg2TXBUdXV1YURtUWstVTQ3bmZjWVZGbFlpLTMwIn0.eyJleHAiOjE2ODAwODMxMjIsImlhdCI6MTY4MDA4MTMyMiwianRpIjoiYTJlMmJiODQtNjYzZS00ZjEwLWFiZjgtMzNlZDNmY2EyN2Q5IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay10c3BsYXRmb3JtLnRlY2hzb3BoeS5jb20vYXV0aC9yZWFsbXMvdGVjaHNvcGh5LXBsYXRmb3JtIiwiYXVkIjpbImNhbXVuZGEtcmVzdC1hcGkiLCJyZWFsbS1tYW5hZ2VtZW50IiwidGlja2V0aW5nLXN5c3RlbSIsImFjY291bnQiXSwic3ViIjoiMjdhZTdiYjEtYTQ0Yi00ZWZkLTkzY2MtMDcxN2I0OWU1ZDNmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY2FtdW5kYS1pZGVudGl0eS1zZXJ2aWNlIiwic2Vzc2lvbl9zdGF0ZSI6IjI5MzEwMzE4LTZkNTItNDMwMS1iMjVhLTFlNWE5YjdmMTUyZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiIsImh0dHA6Ly9sb2NhbGhvc3Q6MzAwMSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIkFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LXJlYWxtIiwidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiY2FtdW5kYS1pZGVudGl0eS1zZXJ2aWNlIjp7InJvbGVzIjpbImF3Z21lbnQtbWFuYWdlLWdyb3VwcyIsImF3Z21lbnQtZXNjYWxhdGlvbi1tYXRyaXgtYWxsIiwiYXdnbWVudC1jYXNlLWluYm94IiwiYXdnbWVudC13b3JrZmxvdy1hbGwiLCJhd2dtZW50LW1hbmFnZS10ZW1wbGF0ZXMiLCJhd2dtZW50LWFkbWluLWFsbCIsIk1hbmFnZXIiLCJhd2dtZW50LXdvcmtmbG93LWVuZ2luZS1yZWFkIiwiYXdnbWVudC1ydW50aW1lLXRlbXBsYXRlLWFsbCIsImF3Z21lbnRfc3VpdGVfY3JtX2NyZWF0ZV9vcl91cGRhdGUiLCJhd2dtZW50LWRlcGxveW1lbnQtYWxsIiwiYXdnbWVudC11eC1jb250cm9sbGVyLWFsbCIsImF3Z21lbnQtY29ubmVjdG9yLXN1aXRlLWNybS1hbGwiLCJhd2dtZW50LXN1cGVyc2V0LXB1YmxpYyIsImF3Z21lbnQtZWxhc3RpYy1zZWFyY2gtYWxsIiwiYXdnbWVudC1tb2RlbGVyLWNhc2UiLCJhd2dtZW50LWRtcy1hbGwiLCJhd2dtZW50LW1vZGVsZXItZm9ybSIsImF3Z21lbnRfc3VpdGVfY3JtX2RlbGV0ZSIsImF3Z21lbnQtY29ubmVjdG9yLWluc3RhLWFsbCIsImF3Z21lbnQtYWNsLWNyZWF0ZS1vci11cGRhdGUiLCJhd2dtZW50LXRlbXBsYXRlcy1hbGwiLCJhd2dtZW50LXN1cGVyc2V0LWFkbWluIiwiYXdnbWVudC1ydW50aW1lLWZvcm0tYWxsIiwiYXdnbWVudF9zdWl0ZV9jcm1fcmVhZCIsImF3Z21lbnQtc3VwZXJzZXQtZ2FtbWEiLCJhd2dtZW50LWRtcy1jcmVhdGUtb3ItdXBkYXRlIiwiYXdnbWVudC1ydW50aW1lLWVzY2FsYXRpb24tbWF0cml4LWFsbCIsImF3Z21lbnQtbWFuYWdlLWNoZWNrbGlzdCIsImF3Z21lbnQtZm9ybS1hbGwiLCJhd2dtZW50LWZpbGUtdXBsb2FkIiwiYXdnbWVudC1tYW5hZ2UtYXJ0aWZhY3RzIiwiYXdnbWVudC1wY2ktYWxsIiwiYXdnbWVudC1tYW5hZ2UtdXNlcnMiLCJhd2dtZW50LWNoZWNrbGlzdC1hbGwiLCJhd2dtZW50LW1vZGVsZXItdGVzdCIsImF3Z21lbnQtY2hlY2tsaXN0LWFkbWluIiwiYXdnbWVudC1hY2NvdW50LWFsbCIsImF3Z21lbnQtcnVsZS1lbmdpbmUtYWxsIiwiYXdnbWVudC1zdXBlcnNldC1hbHBoYSIsImF3Z21lbnQtd29ya2Zsb3ctZW5naW5lLWNyZWF0ZS1vci11cGRhdGUiLCJhd2dtZW50LW1vZGVsZXItcnVsZSIsImF3Z21lbnQtY2FzZS1hbGwiLCJhd2dtZW50LW1vZGVsZXItd29ya2Zsb3ciLCJhd2dtZW50LW1hbmFnZS1jb21wb25lbnRzIiwiYXdnbWVudC1mb3JtLWFjbC1hbGwiLCJhd2dtZW50LW5vdGlmaWNhdGlvbi1hbGwiLCJBZG1pbiIsImF3Z21lbnQtcnVsZS1hbGwiLCJhd2dtZW50LW1hbmFnZS10aGVtZXMiLCJhd2dtZW50LXdvcmtmbG93LWVuZ2luZS1kZWxldGUiLCJhd2dtZW50LXNjaGVkdWxlci1hbGwiLCJhd2dtZW50LXV0aWwtYWxsIiwiYXdnbWVudC1jaGVja2xpc3QtZW5naW5lLWFsbCIsImF3Z21lbnQtZG9jdW1lbnQtY29udmVydGVyLWFsbCIsImF3Z21lbnQtc3VwZXJzZXQtbWFuYWdlciIsImF3Z21lbnQtc3VwZXJzZXQtc3FsX2xhYiIsImF3Z21lbnQtc3VwZXJzZXQtZ3JhbnRlciIsImF3Z21lbnQtYWNsLXJlYWQiXX0sInRpY2tldGluZy1zeXN0ZW0iOnsicm9sZXMiOlsidGlja2V0aW5nLXN5c3RlbS11c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsIm1hbmFnZS1jb25zZW50Iiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJjYW11bmRhLXJlc3QtYXBpIHByb2ZpbGUgZW1haWwgYXdnbWVudCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InZhaWJoYXYgamFpc3dhbCIsImdyb3VwcyI6WyIvY2FtdW5kYS1hZG1pbiIsIi9zdXBlci1hZG1pbiIsIi90cy11c2VyIl0sInByZWZlcnJlZF91c2VybmFtZSI6InZhaWJoYXYiLCJnaXZlbl9uYW1lIjoidmFpYmhhdiIsImZhbWlseV9uYW1lIjoiamFpc3dhbCIsInVzZXJJZCI6IjEwNzUzNTExNTA3NjI2NDM0NTYiLCJlbWFpbCI6InZhaWJoYXYuakB0ZWNoc29waHkuY29tIiwiY2xpZW50Um9sZXMiOlsiYXdnbWVudC1tYW5hZ2UtZ3JvdXBzIiwiYXdnbWVudC1lc2NhbGF0aW9uLW1hdHJpeC1hbGwiLCJhd2dtZW50LWNhc2UtaW5ib3giLCJhd2dtZW50LXdvcmtmbG93LWFsbCIsImF3Z21lbnQtbWFuYWdlLXRlbXBsYXRlcyIsImF3Z21lbnQtYWRtaW4tYWxsIiwiTWFuYWdlciIsImF3Z21lbnQtd29ya2Zsb3ctZW5naW5lLXJlYWQiLCJhd2dtZW50LXJ1bnRpbWUtdGVtcGxhdGUtYWxsIiwiYXdnbWVudF9zdWl0ZV9jcm1fY3JlYXRlX29yX3VwZGF0ZSIsImF3Z21lbnQtZGVwbG95bWVudC1hbGwiLCJhd2dtZW50LXV4LWNvbnRyb2xsZXItYWxsIiwiYXdnbWVudC1jb25uZWN0b3Itc3VpdGUtY3JtLWFsbCIsImF3Z21lbnQtc3VwZXJzZXQtcHVibGljIiwiYXdnbWVudC1lbGFzdGljLXNlYXJjaC1hbGwiLCJhd2dtZW50LW1vZGVsZXItY2FzZSIsImF3Z21lbnQtZG1zLWFsbCIsImF3Z21lbnQtbW9kZWxlci1mb3JtIiwiYXdnbWVudF9zdWl0ZV9jcm1fZGVsZXRlIiwiYXdnbWVudC1jb25uZWN0b3ItaW5zdGEtYWxsIiwiYXdnbWVudC1hY2wtY3JlYXRlLW9yLXVwZGF0ZSIsImF3Z21lbnQtdGVtcGxhdGVzLWFsbCIsImF3Z21lbnQtc3VwZXJzZXQtYWRtaW4iLCJhd2dtZW50LXJ1bnRpbWUtZm9ybS1hbGwiLCJhd2dtZW50X3N1aXRlX2NybV9yZWFkIiwiYXdnbWVudC1zdXBlcnNldC1nYW1tYSIsImF3Z21lbnQtZG1zLWNyZWF0ZS1vci11cGRhdGUiLCJhd2dtZW50LXJ1bnRpbWUtZXNjYWxhdGlvbi1tYXRyaXgtYWxsIiwiYXdnbWVudC1tYW5hZ2UtY2hlY2tsaXN0IiwiYXdnbWVudC1mb3JtLWFsbCIsImF3Z21lbnQtZmlsZS11cGxvYWQiLCJhd2dtZW50LW1hbmFnZS1hcnRpZmFjdHMiLCJhd2dtZW50LXBjaS1hbGwiLCJhd2dtZW50LW1hbmFnZS11c2VycyIsImF3Z21lbnQtY2hlY2tsaXN0LWFsbCIsImF3Z21lbnQtbW9kZWxlci10ZXN0IiwiYXdnbWVudC1jaGVja2xpc3QtYWRtaW4iLCJhd2dtZW50LWFjY291bnQtYWxsIiwiYXdnbWVudC1ydWxlLWVuZ2luZS1hbGwiLCJhd2dtZW50LXN1cGVyc2V0LWFscGhhIiwiYXdnbWVudC13b3JrZmxvdy1lbmdpbmUtY3JlYXRlLW9yLXVwZGF0ZSIsImF3Z21lbnQtbW9kZWxlci1ydWxlIiwiYXdnbWVudC1jYXNlLWFsbCIsImF3Z21lbnQtbW9kZWxlci13b3JrZmxvdyIsImF3Z21lbnQtbWFuYWdlLWNvbXBvbmVudHMiLCJhd2dtZW50LWZvcm0tYWNsLWFsbCIsImF3Z21lbnQtbm90aWZpY2F0aW9uLWFsbCIsIkFkbWluIiwiYXdnbWVudC1ydWxlLWFsbCIsImF3Z21lbnQtbWFuYWdlLXRoZW1lcyIsImF3Z21lbnQtd29ya2Zsb3ctZW5naW5lLWRlbGV0ZSIsImF3Z21lbnQtc2NoZWR1bGVyLWFsbCIsImF3Z21lbnQtdXRpbC1hbGwiLCJhd2dtZW50LWNoZWNrbGlzdC1lbmdpbmUtYWxsIiwiYXdnbWVudC1kb2N1bWVudC1jb252ZXJ0ZXItYWxsIiwiYXdnbWVudC1zdXBlcnNldC1tYW5hZ2VyIiwiYXdnbWVudC1zdXBlcnNldC1zcWxfbGFiIiwiYXdnbWVudC1zdXBlcnNldC1ncmFudGVyIiwiYXdnbWVudC1hY2wtcmVhZCJdfQ.K4Fi7tun3yAEGiSovtX2zMdcYXwr-qsZ7mrRW5LIBNYagaf2aoE4WYEsX1GCV7Yn7Wj65MFLB7Q67dQ_eadBPX9Sw_fTORbCrYCj_NLcKY8l8PsSlWcptfbqpXdi0muRvU-PwYysSwBHzl0a27cPYvbMhDG4bWm8Lkkh2prMnLvA6YzuKcQb0LpCEgoRCfcLWgcCNbWVZzWnjCGuzOHbBqnFEgbOqiCSjCWTLA0JTFwjpq-NPh2ehsPr7ikSVfo5-FE-GwNUwyAybn_pRrMpEUXXeiJNclk1x4Y6LgXS7KvIscng2sHJAUtOxVK7nbwN-rCXnjn3xsp_vARcTr9QlA")
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional()
                .orElseThrow();
        ResponseBaseModel<UserFormDataDefinition> responseBaseModel= objectMapper.readValue(result, ResponseBaseModel.class);
        return objectMapper.convertValue(responseBaseModel.getData(),UserFormDataDefinition.class);
    }
}
