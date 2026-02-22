package tn.enicar.eniconnect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.enicar.eniconnect.model.Post;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.CommentRepository;
import tn.enicar.eniconnect.repository.PostRepository;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires du service des publications.
 */
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void getAllPosts_Success() {
        // Arrange
        Post post1 = Post.builder().id(1L).content("Post 1").build();
        Post post2 = Post.builder().id(2L).content("Post 2").build();
        when(postRepository.findAllOrderByCreatedAtDesc()).thenReturn(Arrays.asList(post1, post2));

        // Act
        List<Post> result = postService.getAllPosts();

        // Assert
        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAllOrderByCreatedAtDesc();
    }

    @Test
    void createPost_Success() {
        // Arrange
        User author = User.builder().id(1L).firstName("Mohamed").lastName("Jerbi").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(author));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(1L);
            return post;
        });

        // Act
        Post result = postService.createPost(1L, "Test content", null);

        // Assert
        assertNotNull(result);
        assertEquals("Test content", result.getContent());
        assertEquals(author, result.getAuthor());
    }

    @Test
    void getPostById_NotFound() {
        // Arrange
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            postService.getPostById(999L);
        });
    }
}
