package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.controllers;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.LoginDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.SignUpDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.UserDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.AuthService;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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

}
