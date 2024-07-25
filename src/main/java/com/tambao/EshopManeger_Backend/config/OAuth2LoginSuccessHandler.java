package com.tambao.EshopManeger_Backend.config;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Role;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.mapper.UserMapper;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.service.Impl.JwtService;
import com.tambao.EshopManeger_Backend.service.Impl.RoleService;
import com.tambao.EshopManeger_Backend.service.Impl.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String FRONT_END_URL = "http://localhost:3000";

    @Autowired
    public OAuth2LoginSuccessHandler(JwtService jwtService, UsersService usersService, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken auth2Authentication = (OAuth2AuthenticationToken) authentication;
        if("google".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            Users existingUser = usersService.findByEmail(email);
            if (existingUser == null) {
                UserDto newUser = new UserDto();
                newUser.setEmail(email);
                newUser.setFullName(name);
                newUser.setSource("GOOGLE");
                String username = generateRandom();
                String generatedPassword = generateRandom();
                String hashedPassword = bCryptPasswordEncoder.encode(generatedPassword);
                newUser.setUserName(username);
                newUser.setPassword(hashedPassword);
                saveWithLoginOAuth2(newUser);
                String token = jwtService.generateToken(username);
                response.sendRedirect(FRONT_END_URL + "/oauth2/redirect?code=" + token);
            } else {
                if (existingUser.getSource().equals("GOOGLE")) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    response.sendRedirect(FRONT_END_URL+ "/oauth2/redirect?code=" + token);
                } else {
                    response.sendRedirect(FRONT_END_URL + "/login?error-login-google");
                }
            }
        } else if ("facebook".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String idEmail = oAuth2User.getAttribute("id");
            String name = oAuth2User.getAttribute("name");
            Users existingUser = usersService.findByEmail(idEmail);
            if (existingUser == null) {
                UserDto newUser = new UserDto();
                newUser.setEmail(idEmail);
                newUser.setFullName(name);
                newUser.setSource("FACEBOOK");
                String username = generateRandom();
                newUser.setUserName(username);
                newUser.setPassword(bCryptPasswordEncoder.encode(generateRandom()));
                saveWithLoginOAuth2(newUser);
                String token = jwtService.generateToken(username);
                response.sendRedirect(FRONT_END_URL + "/oauth2/redirect?code=" + token);
            } else {
                if (existingUser.getSource().equals("FACEBOOK")) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    response.sendRedirect(FRONT_END_URL+ "/oauth2/redirect?code=" + token);
                } else {
                    response.sendRedirect(FRONT_END_URL + "/login?error-login-facebook");
                }
            }
        }else if ("github".equals(auth2Authentication.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer idGitHub = oAuth2User.getAttribute("id");
            String name = oAuth2User.getAttribute("name");
            String avatar_url = oAuth2User.getAttribute("avatar_url");
            Users existingUser = usersService.findByEmail(idGitHub+"");
            if (existingUser == null) {
                UserDto newUser = new UserDto();
                newUser.setEmail(idGitHub+"");
                newUser.setFullName(name);
                newUser.setSource("GITHUB");
                newUser.setAvatar(avatar_url);
                String username = generateRandom();
                newUser.setUserName(username);
                newUser.setPassword(bCryptPasswordEncoder.encode(generateRandom()));
                saveWithLoginOAuth2(newUser);
                String token = jwtService.generateToken(username);
                response.sendRedirect(FRONT_END_URL + "/oauth2/redirect?code=" + token);
            } else {
                if (existingUser.getSource().equals("GITHUB")) {
                    String token = jwtService.generateToken(existingUser.getUserName());
                    response.sendRedirect(FRONT_END_URL+ "/oauth2/redirect?code=" + token);
                } else {
                    response.sendRedirect(FRONT_END_URL + "/login?error-login-github");
                }
            }
        }
    }

    private String generateRandom() {
        return UUID.randomUUID().toString();
    }

    private Users saveWithLoginOAuth2(UserDto userDto) {
        try{
            Role role = roleService.getByName("USER");
            if (role == null) {
                role = new Role();
                role.setName("USER");
                roleService.addRole(role);
            }
            userDto.setEnabled(true);
            Users user = UserMapper.mapToUsers(userDto);
            user.setRoles(List.of(role));
            usersService.saveWithOAuth2(user);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

