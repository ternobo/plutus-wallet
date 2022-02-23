package com.ternobo.wallet.user.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ternobo.wallet.tools.audits.TimestampAudit;
import com.ternobo.wallet.wallet.records.Wallet;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends TimestampAudit implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    @Builder.Default
    private boolean isEnabled = true;

    @Column
    @Builder.Default
    private Role role = Role.USER;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Wallet> wallets;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
