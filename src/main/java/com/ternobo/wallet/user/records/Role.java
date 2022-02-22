package com.ternobo.wallet.user.records;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum Role {
    USER, ADMIN, TRANSACTION_MANAGER, USER_MANAGER;

    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(this.name())
        );
    }

}
