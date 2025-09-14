package tech.buildrun.agregadorinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.agregadorinvestimentos.controller.*;
import tech.buildrun.agregadorinvestimentos.entity.Account;
import tech.buildrun.agregadorinvestimentos.entity.BillingAddress;
import tech.buildrun.agregadorinvestimentos.entity.User;
import tech.buildrun.agregadorinvestimentos.repository.AccountRepository;
import tech.buildrun.agregadorinvestimentos.repository.BillingAddressRepository;
import tech.buildrun.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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

        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();


    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<UserResponseDto> listUsers() {
        var users = userRepository.findAll();



        return users
                .stream()
                .map((us) -> {

                    var accounts = us.getAccounts()
                            .stream()
                            .map(ac ->
                                    new AccountResponseDto(
                                            ac.getAccountId().toString(),
                                            ac.getDescription()
                                    ))
                            .toList();


                    return new UserResponseDto(
                            us.getUserId().toString(),
                            us.getUsername(),
                            us.getEmail(),
                            accounts
                    );
                }).toList();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);

        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);
        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> Entity
        var account = new Account(
                null,
                null,
                createAccountDto.description(),
                user,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);



        var billingAddress = new BillingAddress(
                null,
                accountCreated,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);

    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac ->
                        new AccountResponseDto(
                                ac.getAccountId().toString(),
                                ac.getDescription()
                        ))
                .toList();
    }
}
