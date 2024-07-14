package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.JwtResponse;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.security.LoginRequest;
import com.tambao.EshopManeger_Backend.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/existsByUserName")
    public ResponseEntity<Boolean> existsByUserName(@RequestParam("userName") String username) {
        Boolean checkUserName = userRepository.existsByUserName(username);
        return ResponseEntity.ok(checkUserName);
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam("email") String email) {
        Boolean checkEmail = userRepository.existsByEmail(email);
        return ResponseEntity.ok(checkEmail);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody UserDto userDto){
        userDto.setId(0);
        userDto = accountService.registerUser(userDto);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JwtResponse response = accountService.getMyInfo(username);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<?> active(@RequestParam("email") String email){
        return accountService.activeAccount(email);
    }
}
