package com.brights.webblog_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long id;

    @NotNull
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 letters" )
    private @Getter @Setter String firstName;

    @NotNull
    @Size(min = 2, max = 50, message = "Last name should be between 2 and 50 letters" )
    private @Getter @Setter String lastName;

    @NotNull
    @Email(message = "Email pattern it's not correct")
    @NotEmpty(message = "Email can't be empty")
    private @Getter @Setter String email;

    @OneToMany(mappedBy = "user")
    private @Getter @Setter List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private @Getter @Setter List<PostComment> postcomment = new ArrayList<>();
}
