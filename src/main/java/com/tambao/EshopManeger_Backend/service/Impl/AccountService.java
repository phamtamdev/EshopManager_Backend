package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.JwtResponse;
import com.tambao.EshopManeger_Backend.dto.OAuthDto;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Role;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.UserMapper;
import com.tambao.EshopManeger_Backend.repository.RoleRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByUserName(userDto.getUserName())) {
            throw new ResourceNotFoundException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }
        userDto.setEnabled(true);
        Users user = UserMapper.mapToUsers(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setCreationDate(LocalDate.now());
        Role role = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<>();
        if (role == null) {
            Role newRole = new Role();
            newRole.setName("USER");
            roleRepository.save(newRole);
            user.setRoles(List.of(newRole));
        } else {
            roles.add(role);
            user.setRoles(roles);
        }
        user.setSource("LOCAL");
        userRepository.save(user);
//        sendEmailActive(user.getEmail());
        return UserMapper.mapToUsersDto(user);
    }

    public JwtResponse registerWithOauth(OAuthDto oAuthDto) {
        Users users = new Users();
        users.setEmail(oAuthDto.getEmail());
        users.setFullName(oAuthDto.getFullName());
        users.setUserName(oAuthDto.getUserName());
        users.setSource(oAuthDto.getSource());
        users.setPhone(oAuthDto.getPhone());
        users.setEnabled(true);
        users.setCreationDate(LocalDate.now());
        users.setAvatar(oAuthDto.getAvatar());
        users.setSex(0);
        users.setBirthdate(oAuthDto.getBirthDate());
        users.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            Role newRole = new Role();
            newRole.setName("USER");
            roleRepository.save(newRole);
            users.setRoles(List.of(newRole));
        } else {
            users.setRoles(List.of(role));
        }
        Users usersNew = userRepository.save(users);

        String jwt = jwtService.generateToken(usersNew.getUserName());
        JwtResponse response = new JwtResponse();
        response.setToken(jwt);
        response.setStatusCode(200);
        response.setExpirationTime("24 Hrs");
        response.setMessage("Successfully logged in with OAuth");

        List<String> roles = new ArrayList<>();
        usersNew.getRoles().forEach(r -> roles.add(r.getName()));
        response.setRoles(roles);
        return response;
    }

    public JwtResponse login(LoginRequest loginRequest) {
        JwtResponse response = new JwtResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            Users user = userRepository.findByUserName(loginRequest.getUsername());
            String jwt = jwtService.generateToken(loginRequest.getUsername());
            response.setToken(jwt);
            response.setStatusCode(200);
            List<String> roles = new ArrayList<>();
            user.getRoles().forEach(role -> roles.add(role.getName()));
            response.setRoles(roles);
            response.setExpirationTime("24 Hrs");
            response.setMessage("Successfully logged in");
        } catch (DisabledException e) {
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            response.setMessage("User is disabled");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public UserDto updateInfo(Integer userId, UserDto userDto) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFullName(userDto.getFullName());
        user.setSex(userDto.getSex());
        if(userDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        Users userUpdate = userRepository.save(user);
        return UserMapper.mapToUsersDto(userUpdate);
    }

    public JwtResponse getMyInfo(String userName) {
        JwtResponse response = new JwtResponse();
        try {
            Users user = userRepository.findByUserName(userName);
            if (user != null) {
                response.setStatusCode(200);
                response.setMessage("Successfully");
                response.setUserDto(UserMapper.mapToUsersDto(user));
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public void sendEmailActive(String email) {
        String subject = "Kích Hoạt Tài Khoản Của Bạn Tại Web EShop Manager!";
        String url = "http://localhost:3000/active/" + email;
        String body = "Click Vào Đường Link Để Kích Hoạt Tài Khoản: ";
        body += "<br/> <a href= " + url + ">" + url + "</a> ";
        emailService.sendEmail("tambao11223344@gmail.com", email, subject, body);
    }

    public void sendEmailOtp(String email, String otp) {
        String subject = "Mã OTP thay đổi mật khẩu!";
        String body = "<b>Eshop:</b> Mã OTP đăng nhập website của Quý khách là: " + otp + ". Để đảm bảo an toàn, vui lòng không chia sẻ mã xác thực với bất kỳ ai.";
        emailService.sendEmail("tambao11223344@gmail.com", email, subject, body);
    }

    public ResponseEntity<?> activeAccount(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng này!");
        }

        if (user.isEnabled()) {
            return ResponseEntity.badRequest().body("Tài Khoản Đã Được Kích Hoạt!");
        }
        user.setEnabled(true);
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok("Account activated");
    }

    public ResponseEntity<?> resetPassword(UserDto userDto) {
        Users user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password reset");
    }

    public ResponseEntity<?> otpSend(String email, String otp) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        sendEmailOtp(email, otp);
        return ResponseEntity.ok("OTP send");
    }
}
