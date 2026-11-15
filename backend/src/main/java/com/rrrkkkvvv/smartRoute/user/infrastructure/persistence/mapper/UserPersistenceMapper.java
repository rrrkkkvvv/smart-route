package com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.mapper;

import com.rrrkkkvvv.smartRoute.user.domain.model.User;
import com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper   {
    public User toDomain(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getPasswordHash()
        );
    }
    public UserEntity toEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.id());

        userEntity.setEmail(user.email());
        userEntity.setName(user.name());
        userEntity.setPasswordHash(user.passwordHash());
        return userEntity;
    }
}
