package com.example.template.service.impl;

import com.example.template.domain.entity.User;
import com.example.template.domain.enums.UserStatus;
import com.example.template.dto.request.CreateUserRequest;
import com.example.template.dto.request.UpdateUserRequest;
import com.example.template.dto.response.UserResponse;
import com.example.template.exception.ResourceNotFoundException;
import com.example.template.mapper.UserMapper;
import com.example.template.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Unit test for {@link UserServiceImpl}.
 *
 * <p>Uses Mockito — no Spring context is loaded.
 * Repository and mapper are mocked to isolate business logic.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    private final UUID userId = UUID.randomUUID();
    private final Instant now = Instant.now();

    // ── create ──────────────────────────────────────────────────────────────

    @Test
    void create_validRequest_returnsSavedUser() {
        CreateUserRequest request = new CreateUserRequest("jane@example.com", "Jane", "Doe");
        User entity = User.builder()
                .email("jane@example.com").firstName("Jane").lastName("Doe").build();
        User savedEntity = User.builder()
                .id(userId).email("jane@example.com").firstName("Jane").lastName("Doe")
                .status(UserStatus.ACTIVE).createdAt(now).updatedAt(now).build();
        UserResponse expectedResponse = new UserResponse(
                userId, "jane@example.com", "Jane", "Doe", UserStatus.ACTIVE, now, now);

        given(userMapper.toEntity(request)).willReturn(entity);
        given(userRepository.save(entity)).willReturn(savedEntity);
        given(userMapper.toResponse(savedEntity)).willReturn(expectedResponse);

        UserResponse result = userService.create(request);

        assertThat(result.id()).isEqualTo(userId);
        assertThat(result.email()).isEqualTo("jane@example.com");
        then(userRepository).should().save(entity);
    }

    // ── findById ────────────────────────────────────────────────────────────

    @Test
    void findById_existingUser_returnsResponse() {
        User entity = User.builder()
                .id(userId).email("jane@example.com").firstName("Jane").lastName("Doe")
                .status(UserStatus.ACTIVE).createdAt(now).updatedAt(now).build();
        UserResponse expected = new UserResponse(
                userId, "jane@example.com", "Jane", "Doe", UserStatus.ACTIVE, now, now);

        given(userRepository.findById(userId)).willReturn(Optional.of(entity));
        given(userMapper.toResponse(entity)).willReturn(expected);

        UserResponse result = userService.findById(userId);

        assertThat(result.email()).isEqualTo("jane@example.com");
    }

    @Test
    void findById_nonExistingUser_throwsResourceNotFound() {
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(userId.toString());
    }

    // ── update ──────────────────────────────────────────────────────────────

    @Test
    void update_existingUser_appliesPatchAndReturns() {
        UpdateUserRequest request = new UpdateUserRequest("Janet", null);
        User entity = User.builder()
                .id(userId).email("jane@example.com").firstName("Jane").lastName("Doe")
                .status(UserStatus.ACTIVE).createdAt(now).updatedAt(now).build();
        UserResponse expected = new UserResponse(
                userId, "jane@example.com", "Janet", "Doe", UserStatus.ACTIVE, now, now);

        given(userRepository.findById(userId)).willReturn(Optional.of(entity));
        given(userRepository.save(entity)).willReturn(entity);
        given(userMapper.toResponse(entity)).willReturn(expected);

        UserResponse result = userService.update(userId, request);

        assertThat(result.firstName()).isEqualTo("Janet");
        then(userMapper).should().updateEntity(request, entity);
    }

    @Test
    void update_nonExistingUser_throwsResourceNotFound() {
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(userId, new UpdateUserRequest("X", null)))
                .isInstanceOf(ResourceNotFoundException.class);

        then(userRepository).should(never()).save(any());
    }

    // ── delete ──────────────────────────────────────────────────────────────

    @Test
    void delete_existingUser_deletesSuccessfully() {
        given(userRepository.existsById(userId)).willReturn(true);

        userService.delete(userId);

        then(userRepository).should().deleteById(userId);
    }

    @Test
    void delete_nonExistingUser_throwsResourceNotFound() {
        given(userRepository.existsById(userId)).willReturn(false);

        assertThatThrownBy(() -> userService.delete(userId))
                .isInstanceOf(ResourceNotFoundException.class);

        then(userRepository).should(never()).deleteById(any());
    }
}
