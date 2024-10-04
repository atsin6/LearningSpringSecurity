package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.LoginDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDto loginDto) {

        //AuthenticationManager -> ProviderManager -> AuthenticationProviders
        //AuthenticationProviders -> DaoAuthenticationProvider (One of many authentication provider)
        //DaoAuthenticationProvider -> UserService implements UserDetailService
        //UserService implements UserDetailService -> call override method loadUserByUserName() for fetching user
        //In the username it will pass the emailId that we pass in the below function
        //then we get the data from the repository and
        //now we match the password with the password encoder
        //if password is matched then it return the new authentication object
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Casting the authentication principal to a User object
        User user = (User) authentication.getPrincipal();

        // A JWT token is generated for the authenticated user
        String token = jwtService.generateToken(user);

        // The token can be stored in the client's browser, often in cookies or local storage
        return token;
    }
}
