package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.LoginDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.SignUpDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.dto.UserDto;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.exceptions.ResourceNotFoundException;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    //we won't be directly using this method spring security is using this
    // to load the user data by calling the userRepository
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("user not found with email: " + username));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id: " + userId)
        );
    }

    public UserDto signup(SignUpDto signUpDto) {
        //Checking that if user is signed up with this email
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()) {
            throw new BadCredentialsException("Email already in use"+signUpDto.getEmail());
        }
        //Creating a User from signUpDto details
        User toBeCreatedUser = modelMapper.map(signUpDto, User.class);

        //Encoding Password for security purposes
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        //saving details in the database
        User savedUser = userRepository.save(toBeCreatedUser);

        //giving back the details of the newly created user
        return modelMapper.map(savedUser, UserDto.class);
    }


}
