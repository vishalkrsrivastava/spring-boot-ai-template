package com.example.template.mapper;

import com.example.template.domain.entity.User;
import com.example.template.dto.request.CreateUserRequest;
import com.example.template.dto.request.UpdateUserRequest;
import com.example.template.dto.response.UserResponse;
import org.mapstruct.*;

/**
 * MapStruct mapper between {@link User} entity and its DTOs.
 *
 * <p>The implementation is auto-generated at compile time and registered as a Spring bean.
 * Use {@code NullValuePropertyMappingStrategy.IGNORE} on updates to achieve patch semantics.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /** Map entity → response DTO. */
    UserResponse toResponse(User user);

    /** Map creation request → new entity (id and timestamps are set by JPA). */
    User toEntity(CreateUserRequest request);

    /**
     * Apply non-null fields from {@code request} onto an existing {@code user}.
     * Null fields in the request are ignored (patch semantics).
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}
