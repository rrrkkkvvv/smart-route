package com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.adapter;

import com.rrrkkkvvv.smartRoute.user.application.port.out.UserRepository;
import com.rrrkkkvvv.smartRoute.user.domain.model.User;
import com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.entity.UserEntity;
import com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.rrrkkkvvv.smartRoute.user.infrastructure.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository springDataRepository;
    private final UserPersistenceMapper mapper;
    public UserRepositoryAdapter(
            SpringDataUserRepository springDataRepository,
            UserPersistenceMapper mapper
    ) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return springDataRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);

        UserEntity saved = springDataRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }
}
