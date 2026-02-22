package tn.enicar.eniconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.enicar.eniconnect.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();

    List<Post> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
}
