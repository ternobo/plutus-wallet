package com.ternobo.wallet.user.records;

import com.ternobo.wallet.tools.audits.TimestampAudit;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
public class User extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;
}
