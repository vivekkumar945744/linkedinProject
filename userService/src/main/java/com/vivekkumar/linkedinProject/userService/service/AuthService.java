package com.vivekkumar.linkedinProject.userService.service;

import com.vivekkumar.linkedinProject.userService.dto.LoginRequestDto;
import com.vivekkumar.linkedinProject.userService.dto.SignupRequestDto;
import com.vivekkumar.linkedinProject.userService.dto.UserDto;
import com.vivekkumar.linkedinProject.userService.entity.User;
import com.vivekkumar.linkedinProject.userService.exception.ResourceNotFoundException;
import com.vivekkumar.linkedinProject.userService.repository.UserRepository;
import com.vivekkumar.linkedinProject.userService.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException {
        log.info("Signup a user with email: {}", signupRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("User already exists");
        }
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email: {}", loginRequestDto.getEmail());

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email id: " + loginRequestDto.getEmail()));

        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new com.vivekkumar.linkedinProject.userService.exception.BadRequestException("Incorrect Password");
        }
        return jwtService.generateAccessToken(user);
    }
}
