package com.ternobo.wallet.user.service;

import com.ternobo.wallet.user.http.exceptions.UsernameDuplicatedException;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.repositories.UserRepository;
import com.ternobo.wallet.wallet.records.Currency;
import com.ternobo.wallet.wallet.records.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JPAUserService implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public JPAUserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserDTO user) throws UsernameDuplicatedException {
        // Throws error if username is duplicated
        if (this.repository.existsByUsername(user.getUsername())) {
            throw new UsernameDuplicatedException();
        }

        return this.repository.save(
                User.builder()
                        .name(user.getName())
                        .password(this.passwordEncoder.encode(user.getPassword()))
                        .username(user.getUsername().toLowerCase())
                        .wallets(List.of(
                                Wallet.builder()
                                        .cacheBalance(0L)
                                        .currency(Currency.PLUTUS)
                                        .token(UUID.randomUUID())
                                        .build()
                        ))
                        .build()
        );
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user with %s username not found".formatted(username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user with %s username not found".formatted(username)));
    }
}
