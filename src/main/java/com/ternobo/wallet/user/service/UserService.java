package com.ternobo.wallet.user.service;

import com.ternobo.wallet.user.http.exceptions.UsernameDuplicatedException;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User createUser(UserDTO user) throws UsernameDuplicatedException;

    User findByUsername(String username);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
