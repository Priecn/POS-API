package com.pos.entity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name="user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "USERNAME", length = 50, unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "EMAIL", length = 50, unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "LAST_PASSWORD_RESET_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @NotNull
    @Column(name = "ENABLED")
    private Boolean enabled;

    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="AUTHORITY_ID", referencedColumnName = "ID")}
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
