package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.controllers;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.LoginDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.SignUpDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.UserDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.SessionEntity;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.AuthService;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.SessionService;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signup(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        String token = authService.login(loginDto);
        //Saving token in cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId) {

        authService.logout(userId);
        return ResponseEntity.ok("You have successfully logged out");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {
        log.info("----------------------------------token: {}",(String) request.getAttribute("Authorization"));
        String token = (String) request.getAttribute("Authorization");
        log.info("token: {}",(String) request.getAttribute("Authorization"));
        authService.logout(token);
        return ResponseEntity.ok("You have successfully logged out");
    }


}
