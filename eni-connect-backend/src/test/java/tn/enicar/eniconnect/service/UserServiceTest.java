package tn.enicar.eniconnect.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.enicar.eniconnect.dto.RegisterRequest;
import tn.enicar.eniconnect.model.Role;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires du service utilisateur.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Mohamed");
        registerRequest.setLastName("Jerbi");
        registerRequest.setEmail("mohamed.jerbi@eni-carthage.tn");
        registerRequest.setPassword("password123");
        registerRequest.setRole("student");
    }

    @Test
    void register_Success() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        // Act
        User result = userService.register(registerRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Mohamed", result.getFirstName());
        assertEquals("Jerbi", result.getLastName());
        assertEquals("mohamed.jerbi@eni-carthage.tn", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_EmailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userService.register(registerRequest);
        });
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByEmail_Found() {
        // Arrange
        User mockUser = User.builder()
                .id(1L)
                .email("test@eni-carthage.tn")
                .build();
        when(userRepository.findByEmail("test@eni-carthage.tn")).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = userService.findByEmail("test@eni-carthage.tn");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@eni-carthage.tn", result.get().getEmail());
    }

    @Test
    void findByEmail_NotFound() {
        // Arrange
        when(userRepository.findByEmail("unknown@test.tn")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findByEmail("unknown@test.tn");

        // Assert
        assertFalse(result.isPresent());
    }
}
