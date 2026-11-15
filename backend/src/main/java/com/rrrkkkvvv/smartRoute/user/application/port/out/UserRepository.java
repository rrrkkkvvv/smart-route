package com.rrrkkkvvv.smartRoute.user.application.port.out;

import com.rrrkkkvvv.smartRoute.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);

}
