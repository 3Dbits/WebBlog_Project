package com.brights.webblog_project.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Title should be between 2 and 50 letters")
    private @Getter @Setter String title;

    @NotNull
    @Size(min = 2, max = 50, message = "MetaTitle should be between 2 and 50 letters")
    private @Getter @Setter String metaTitle;

    @NotNull
    @Size(min = 2, max = 250, message = "Summary should be between 2 and 250 characters")
    private @Getter @Setter String summary;

    private @Getter @Setter boolean published;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private @Getter @Setter Date createAt;

    private @Getter @Setter Date updateAt;

    private @Getter @Setter Date publishedAt;

    @NotNull
    @Size(min = 2, message = "Content should at least have 2 characters")
    private @Getter @Setter String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private @Getter @Setter User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private @Getter @Setter List<PostComment> postComment = new ArrayList<>();
}
