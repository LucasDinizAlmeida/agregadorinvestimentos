package tech.buildrun.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.buildrun.agregadorinvestimentos.controller.CreateUserDto;
import tech.buildrun.agregadorinvestimentos.entity.User;
import tech.buildrun.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        // DTO -> Entity

        var entity = new User(
                createUserDto.password(),
                null,
                Instant.now(),
                createUserDto.email(),
                createUserDto.username(),
                null
//                UUID.randomUUID()

        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();

    }
}
