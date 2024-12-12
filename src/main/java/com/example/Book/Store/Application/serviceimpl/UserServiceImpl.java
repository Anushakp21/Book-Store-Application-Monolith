package com.example.Book.Store.Application.serviceimpl;

import com.example.Book.Store.Application.Handler.UserNotFoundByIdException;
import com.example.Book.Store.Application.entity.User;
import com.example.Book.Store.Application.mapper.UserMapper;
import com.example.Book.Store.Application.repository.UserRepository;
import com.example.Book.Store.Application.requestdto.LoginRequest;
import com.example.Book.Store.Application.requestdto.RegistrationRequest;
import com.example.Book.Store.Application.requestdto.UserRequestDto;
import com.example.Book.Store.Application.responsedto.LoginResponse;
import com.example.Book.Store.Application.responsedto.UserResponseDto;
import com.example.Book.Store.Application.security.Util;
import com.example.Book.Store.Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Util util;

    @Override
    public UserResponseDto registerUser(RegistrationRequest registrationRequest) {
        User user = userMapper.mapToUser(registrationRequest);
        user = userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail())
        );

        String token = util.createToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(user.getEmail());
        loginResponse.setJwtToken(token);
        return loginResponse;
    }

    @Override
    public UserResponseDto findUserById(long userId) {
        return userRepository.findById(userId)
                .map(user -> userMapper.mapToUserResponse(user))
                .orElseThrow(() -> new UserNotFoundByIdException("Failed to find user"));
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> userMapper.mapToUserResponse(user)).toList();
    }

    @Override
    public UserResponseDto updateUserById(long userId, UserRequestDto userRequest)
    {
        return userRepository.findById(userId)
                .map(user -> {
                    user = userMapper.mapToUser(userRequest, user);
                    user.setUpdatedDate(LocalDate.now());
                    userRepository.save(user);
                    return userMapper.mapToUserResponse(user);
                }).orElseThrow(() -> new UserNotFoundByIdException("Failed to update user"));
    }

    @Override
    public UserResponseDto deleteUserById(long userId) {
        return userRepository.findById(userId)
                .map(user ->
                {
                    userRepository.deleteById(userId);
                    return userMapper.mapToUserResponse(user);
                }
                ).orElseThrow(() -> new UserNotFoundByIdException("Failed to update user"));
    }
}
