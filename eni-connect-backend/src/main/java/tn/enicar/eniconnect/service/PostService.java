package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.model.Comment;
import tn.enicar.eniconnect.model.Post;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.CommentRepository;
import tn.enicar.eniconnect.repository.PostRepository;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.List;

@Service
public class PostService {

    private static final Logger logger = LogManager.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        logger.debug("Récupération de tous les posts");
        return postRepository.findAllOrderByCreatedAtDesc();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
    }

    public Post createPost(Long authorId, String content, String imageUrl) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Post post = Post.builder()
                .content(content)
                .imageUrl(imageUrl)
                .author(author)
                .build();

        Post saved = postRepository.save(post);
        logger.info("Nouveau post créé par {} (id={})", author.getFullName(), saved.getId());
        return saved;
    }

    public Post updatePost(Long postId, String content, String imageUrl) {
        Post post = getPostById(postId);
        if (content != null)
            post.setContent(content);
        if (imageUrl != null)
            post.setImageUrl(imageUrl);
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
        logger.info("Post supprimé (id={})", postId);
    }

    public Post toggleLike(Long postId, Long userId) {
        Post post = getPostById(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            logger.debug("Like retiré sur post {} par user {}", postId, userId);
        } else {
            post.getLikes().add(user);
            logger.debug("Like ajouté sur post {} par user {}", postId, userId);
        }
        return postRepository.save(post);
    }

    public Comment addComment(Long postId, Long authorId, String content) {
        Post post = getPostById(postId);
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Comment comment = Comment.builder()
                .content(content)
                .author(author)
                .post(post)
                .build();

        Comment saved = commentRepository.save(comment);
        logger.info("Commentaire ajouté sur post {} par {}", postId, author.getFullName());
        return saved;
    }

    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }
}
