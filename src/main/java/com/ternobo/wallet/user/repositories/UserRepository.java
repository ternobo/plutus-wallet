package com.ternobo.wallet.user.repositories;

import com.ternobo.wallet.user.records.Role;
import com.ternobo.wallet.user.records.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findByRole(Role role);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameIsLikeOrName(String username, String name);

    boolean existsByUsername(String username);

}