package com.tambao.EshopManeger_Backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tambao.EshopManeger_Backend.dto.OAuthDto;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.service.Impl.JwtService;
import com.tambao.EshopManeger_Backend.service.Impl.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UsersService usersService;
    private final String frontEndUrl = "https://eshop-manager-frontend.vercel.app";
//    private final String frontEndUrl = "http://localhost:3000";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public OAuth2LoginSuccessHandler(JwtService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken auth2Authentication = (OAuth2AuthenticationToken) authentication;
        if ("google".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            Users existingUser = usersService.findByEmail(email);
            if (existingUser == null) {
                OAuthDto oAuthDto = new OAuthDto();
                oAuthDto.setEmail(email);
                oAuthDto.setAvatar("");
                oAuthDto.setFullName(name);
                oAuthDto.setSource("GOOGLE");
                String userDtoJson = objectMapper.writeValueAsString(oAuthDto);
                String encodedUserDto = URLEncoder.encode(userDtoJson, StandardCharsets.UTF_8.toString());
                response.sendRedirect(frontEndUrl + "/oauth2/redirect?data=" + encodedUserDto);
            } else {
                if (existingUser.getSource().equals("GOOGLE") && existingUser.isEnabled()) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    List<String> roles = new ArrayList<>();
                    existingUser.getRoles().forEach(role -> roles.add(role.getName()));
                    OAuthResponse oAuthResponse = new OAuthResponse();
                    oAuthResponse.setToken(token);
                    oAuthResponse.setRoles(roles);
                    String json = objectMapper.writeValueAsString(oAuthResponse);
                    String jsonData = URLEncoder.encode(json, StandardCharsets.UTF_8.toString());
                    response.sendRedirect(frontEndUrl + "/oauth2/redirect?token=" + jsonData);
                } else {
                    String errGoogle = frontEndUrl + "/login?error-login-google";
                    if(!existingUser.isEnabled()){
                        errGoogle += "-disabled";
                    }
                    response.sendRedirect(errGoogle);
                }
            }
        } else if ("facebook".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String idEmail = oAuth2User.getAttribute("id");
            String name = oAuth2User.getAttribute("name");
            Users existingUser = usersService.findByEmail(idEmail);
            if (existingUser == null) {
                OAuthDto oAuthDto = new OAuthDto();
                oAuthDto.setEmail(idEmail);
                oAuthDto.setAvatar("");
                oAuthDto.setFullName(name);
                oAuthDto.setSource("FACEBOOK");
                String userDtoJson = objectMapper.writeValueAsString(oAuthDto);
                String encodedUserDto = URLEncoder.encode(userDtoJson, StandardCharsets.UTF_8.toString());
                response.sendRedirect(frontEndUrl + "/oauth2/redirect?data=" + encodedUserDto);
            } else {
                if (existingUser.getSource().equals("FACEBOOK")) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    List<String> roles = new ArrayList<>();
                    existingUser.getRoles().forEach(role -> roles.add(role.getName()));
                    OAuthResponse oAuthResponse = new OAuthResponse();
                    oAuthResponse.setToken(token);
                    oAuthResponse.setRoles(roles);
                    String json = objectMapper.writeValueAsString(oAuthResponse);
                    String jsonData = URLEncoder.encode(json, StandardCharsets.UTF_8.toString());
                    response.sendRedirect(frontEndUrl + "/oauth2/redirect?token=" + jsonData);
                } else {
                    response.sendRedirect(frontEndUrl + "/login?error-login-facebook");
                }
            }
        } else if ("github".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer idGitHub = oAuth2User.getAttribute("id");
            String name = oAuth2User.getAttribute("name");
            String avatar_url = oAuth2User.getAttribute("avatar_url");
            Users existingUser = usersService.findByEmail(idGitHub + "");
            if (existingUser == null) {
                OAuthDto oAuthDto = new OAuthDto();
                oAuthDto.setEmail(idGitHub + "");
                oAuthDto.setAvatar(avatar_url);
                oAuthDto.setFullName(name);
                oAuthDto.setSource("GITHUB");
                String userDtoJson = objectMapper.writeValueAsString(oAuthDto);
                String encodedUserDto = URLEncoder.encode(userDtoJson, StandardCharsets.UTF_8.toString());
                response.sendRedirect(frontEndUrl + "/oauth2/redirect?data=" + encodedUserDto);
            } else {
                if (existingUser.getSource().equals("GITHUB")) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    List<String> roles = new ArrayList<>();
                    existingUser.getRoles().forEach(role -> roles.add(role.getName()));
                    OAuthResponse oAuthResponse = new OAuthResponse();
                    oAuthResponse.setToken(token);
                    oAuthResponse.setRoles(roles);
                    String json = objectMapper.writeValueAsString(oAuthResponse);
                    String jsonData = URLEncoder.encode(json, StandardCharsets.UTF_8.toString());
                    response.sendRedirect(frontEndUrl + "/oauth2/redirect?token=" + jsonData);
                } else {
                    response.sendRedirect(frontEndUrl + "/login?error-login-github");
                }
            }
        }
    }

    @Getter
    @Setter
    public static class OAuthResponse {
        private String token;
        private List<String> roles;
    }
}

