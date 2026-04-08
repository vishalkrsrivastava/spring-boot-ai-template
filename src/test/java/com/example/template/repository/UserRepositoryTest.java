package com.example.template.repository;

import com.example.template.domain.entity.User;
import com.example.template.domain.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository slice test for {@link UserRepository}.
 *
 * <p>Uses {@code @DataJpaTest} to load only the JPA layer with an in-memory H2 database.
 * No web layer or service beans are created.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void save_validUser_generatesUuidAndAuditTimestamps() {
        User user = User.builder()
                .email("repo-test@example.com")
                .firstName("Repo")
                .lastName("Test")
                .build();

        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void findByEmail_existingEmail_returnsUser() {
        User user = User.builder()
                .email("findme@example.com")
                .firstName("Find")
                .lastName("Me")
                .build();
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("findme@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Find");
    }

    @Test
    void findByEmail_nonExistingEmail_returnsEmpty() {
        Optional<User> found = userRepository.findByEmail("ghost@example.com");

        assertThat(found).isEmpty();
    }

    @Test
    void existsByEmail_existingEmail_returnsTrue() {
        userRepository.save(User.builder()
                .email("exists@example.com")
                .firstName("Exists")
                .lastName("Check")
                .build());

        assertThat(userRepository.existsByEmail("exists@example.com")).isTrue();
        assertThat(userRepository.existsByEmail("nope@example.com")).isFalse();
    }
}
