package com.ternobo.wallet.auth.records;

import com.ternobo.wallet.user.records.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "access_tokens")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiration_date")
    private Date expirationDate;

    public AccessToken(String refreshToken, User user, LocalDate jwtExpireDate) {
        this.refreshToken = refreshToken;
        this.user = user;
        this.expirationDate = java.sql.Date.valueOf(jwtExpireDate.plusDays(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccessToken that = (AccessToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
