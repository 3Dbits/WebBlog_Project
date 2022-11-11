package com.brights.webblog_project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private @Getter @Setter User user;

    @Temporal(TemporalType.TIMESTAMP)
    private @Getter @Setter Date createdAt;

    @NotNull
    @Size(min = 2, max = 250, message = "Comment should be between 2 and 250 letters")
    private @Getter @Setter String content;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private @Getter @Setter Post post;
}
