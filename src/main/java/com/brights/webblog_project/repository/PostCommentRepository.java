package com.brights.webblog_project.repository;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    public List<PostComment> findByPost(Post post);
}
