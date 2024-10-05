package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.LoginDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.SessionEntity;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    public String login(LoginDto loginDto) {

        // AuthenticationManager -> ProviderManager
        // ProviderManager -> AuthenticationProviders
        // AuthenticationProviders -> DaoAuthenticationProvider (One of many authentication provider)
        // DaoAuthenticationProvider -> UserService implements UserDetailService
        // UserService implements UserDetailService -> call override method loadUserByUserName() for fetching user
        // In the username it will pass the emailId that we pass in the below function
        // then we get the data from the repository and
        // now we match the password with the password encoder
        // if password is matched then it return the new authentication object
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Casting the authentication principal to a User object
        User user = (User) authentication.getPrincipal();

        // A JWT token is generated for the authenticated user
        String token = jwtService.generateToken(user);

        //creating session
        // Check if the user already has an active session
        if(sessionService.isSessionExistById(user.getId())){
            log.info("Session exists: {}",sessionService.isSessionExistById(user.getId()));
            log.info("Is Session active: {}",sessionService.isUserSessionActive(user.getId()));
            if (sessionService.isUserSessionActive(user.getId())) {
                throw new RuntimeException("User has an already active session.");

            }else{
                sessionService.setSessionActive(user.getId());
                log.info("Is Session active2: {}",sessionService.isUserSessionActive(user.getId()));
            }
        }
        else{
            sessionService.createSession(user.getId(), token);
        }

        // The token can be stored in the client's browser, often in cookies or local storage
        return token;
    }

    public void logout(Long userId) {
        SessionEntity sessionEntity = sessionService.getSessionByUserId(userId);
        String token = sessionEntity.getSessionToken();
        sessionService.deactivateSession(token);
    }
    public void logout(String token) {
        sessionService.deactivateSession(token);
    }

}
