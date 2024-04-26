package com.hank.oauth2clientdemo.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final Gson gson;

    @GetMapping(value = "/")
    public String index(
            Model model,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User,
            @AuthenticationPrincipal Jwt token,
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String headerToken
    ) {
        log.info("securityContext: {}", gson.toJson(securityContext));
        log.info("token: {}", token);
        log.info("headerToken: {}", headerToken);

        // github
        OAuth2AuthorizedClient githubClient = authorizedClientService.loadAuthorizedClient("github", oauth2User.getName());
        if (!ObjectUtils.isEmpty(githubClient)) {
            OAuth2AccessToken accessToken = githubClient.getAccessToken();
            OAuth2AccessToken.TokenType githubTokenType = accessToken.getTokenType();
            Set<String> githubScopes = accessToken.getScopes();
            log.info("github tokenType: {}", githubScopes);
            String githubTokenValue = accessToken.getTokenValue();
            log.info("github tokenValue: {}", githubTokenValue);
        }

        // line
        OAuth2AuthorizedClient lineClient = authorizedClientService.loadAuthorizedClient("line", oauth2User.getName());
        if (!ObjectUtils.isEmpty(lineClient)) {
            OAuth2AccessToken lineAccessToken = lineClient.getAccessToken();
            OAuth2AccessToken.TokenType lineTokenType = lineAccessToken.getTokenType();
            Set<String> lineScopes = lineAccessToken.getScopes();
            log.info("lineScopes: {}", lineScopes);
            String lineTokenValue = lineAccessToken.getTokenValue();
            log.info("lineTokenValue: {}", lineTokenValue);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        log.info("principal: {}", gson.toJson(principal));
        Object credentials = authentication.getCredentials();
        log.info("credentials: {}", gson.toJson(credentials));
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("authorities: {}", gson.toJson(authorities));

//        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        String token = authentication.getToken().getTokenValue();

        model.addAttribute("userName", oauth2User.getName());
        log.info("oauth2User name: {}", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        log.info("oauth2User client name: {}", oauth2User.getName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());
        log.info("oauth2User attribute: {}", oauth2User.getAttributes());
        return "index";
    }

}
