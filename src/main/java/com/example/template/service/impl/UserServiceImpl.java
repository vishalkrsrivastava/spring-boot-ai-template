package com.example.template.service.impl;

import com.example.template.domain.entity.User;
import com.example.template.dto.request.CreateUserRequest;
import com.example.template.dto.request.UpdateUserRequest;
import com.example.template.dto.response.PagedResponse;
import com.example.template.dto.response.UserResponse;
import com.example.template.exception.ResourceNotFoundException;
import com.example.template.mapper.UserMapper;
import com.example.template.repository.UserRepository;
import com.example.template.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link UserService}.
 *
 * <p>Class-level {@code @Transactional(readOnly = true)} applies to all read operations.
 * Write operations override with {@code @Transactional} to enable writes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.email());
        User saved = userRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public PagedResponse<UserResponse> findAll(Pageable pageable) {
        return PagedResponse.from(userRepository.findAll(pageable).map(userMapper::toResponse));
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        userMapper.updateEntity(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
        log.info("Deleted user: {}", id);
    }
}
