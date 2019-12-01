package com.example.contactsexternal.apigateway.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER="Authorization";
  private static final String TOKEN_TYPE = "Bearer";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
    	Jwt details = (Jwt) authentication.getPrincipal();
      requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, details.getTokenValue()));
    }
  }
}
