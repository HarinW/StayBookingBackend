package com.laioffer.staybooking.model;

// data transfer object for user, used to hide sensitive information such as password
public record UserDto(
    Long id,
    String username,
    UserRole role
) {
  public UserDto(UserEntity entity) {
    this(
        entity.getId(),
        entity.getUsername(),
        entity.getRole()
    );
  }
}
