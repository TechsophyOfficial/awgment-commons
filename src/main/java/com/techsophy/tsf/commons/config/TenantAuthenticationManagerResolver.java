package com.techsophy.tsf.commons.config;

import com.techsophy.tsf.commons.user.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.techsophy.tsf.commons.constants.CommonConstants.AUTHORIZATION;


@RefreshScope
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TenantAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest>
{
	private final Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();
	private  String keycloakIssuerUri;
	private final TokenUtils tokenUtils;
	private JWTRoleConverter jwtRoleConverter;

	@Override
	public AuthenticationManager resolve(HttpServletRequest request)
	{
		return this.authenticationManagers.computeIfAbsent(toTenant(request), this::fromTenant);
	}

	private String toTenant(HttpServletRequest request)
	{
		try
		{
			return tokenUtils.getIssuerFromToken(request.getHeader(AUTHORIZATION));
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private AuthenticationManager fromTenant(String tenant)
	{
		JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(keycloakIssuerUri+tenant));
		jwtAuthenticationProvider.setJwtAuthenticationConverter(authenticationConverter());
		return jwtAuthenticationProvider :: authenticate;
	}

	private JwtAuthenticationConverter authenticationConverter()
	{
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwtRoleConverter);
		return converter;
	}
}