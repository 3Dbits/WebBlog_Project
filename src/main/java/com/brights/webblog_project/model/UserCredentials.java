package com.brights.webblog_project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users_credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long id;

    @NotNull
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 letters" )
    @Column(unique = true)
    private @Getter @Setter String username;

    @NotNull
    @Size(min = 8, message = "Password should contain at least 8 characters")
    private @Getter @Setter String password;

    private @Getter @Setter String roles;

    private @Getter @Setter boolean isEnabled;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private @Getter @Setter User user;
}
