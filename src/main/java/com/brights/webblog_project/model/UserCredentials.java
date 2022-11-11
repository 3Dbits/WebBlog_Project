package com.brights.webblog_project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users_credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long id;

    @NotNull
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 letters" )
    private @Getter @Setter String userName;

    @NotNull
    @Min(value = 8, message = "Password should contain at least 8 characters")
    private @Getter @Setter String password;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private @Getter @Setter User user;
}
