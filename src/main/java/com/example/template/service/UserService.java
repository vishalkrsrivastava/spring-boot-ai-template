package com.example.template.service;

import com.example.template.dto.request.CreateUserRequest;
import com.example.template.dto.request.UpdateUserRequest;
import com.example.template.dto.response.PagedResponse;
import com.example.template.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * User management operations.
 *
 * <p>Implemented by {@link com.example.template.service.impl.UserServiceImpl}.
 * Controllers and tests should depend on this interface, not the implementation.
 */
public interface UserService {

    /** Create a new user from the given request. Returns the persisted resource. */
    UserResponse create(CreateUserRequest request);

    /** Return a user by id, or throw {@link com.example.template.exception.ResourceNotFoundException}. */
    UserResponse findById(UUID id);

    /** Return a paginated list of all users. */
    PagedResponse<UserResponse> findAll(Pageable pageable);

    /** Apply non-null fields from {@code request} to the identified user. */
    UserResponse update(UUID id, UpdateUserRequest request);

    /** Permanently remove the identified user. */
    void delete(UUID id);
}
