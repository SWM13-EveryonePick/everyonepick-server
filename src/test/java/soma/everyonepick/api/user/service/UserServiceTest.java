package soma.everyonepick.api.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setClientId("test-client-id");
        testUser.setIsActive(true);
    }

    @Test
    void saveUserTest() {
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(testUser);

        User savedUser = userService.saveUser(testUser);

        assertEquals(testUser.getId(), savedUser.getId());
        assertEquals(testUser.getClientId(), savedUser.getClientId());
    }

    @Test
    void findByClientIdTest() {
        when(userRepository.findByClientIdAndIsActive(testUser.getClientId(), true)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByClientId(testUser.getClientId());

        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getClientId(), foundUser.getClientId());
    }

    @Test
    void findByClientIdThrowsExceptionTest() {
        when(userRepository.findByClientIdAndIsActive(testUser.getClientId(), true)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByClientIdThrowsException(testUser.getClientId());

        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getClientId(), foundUser.getClientId());
    }

    @Test
    void findByClientIdThrowsExceptionNotFoundTest() {
        when(userRepository.findByClientIdAndIsActive(testUser.getClientId(), true)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByClientIdThrowsException(testUser.getClientId()));
    }

    @Test
    void findByIdTest() {
        when(userRepository.findByIdAndIsActive(testUser.getId(), true)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findById(testUser.getId());

        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getClientId(), foundUser.getClientId());
    }

    @Test
    void findByIdNotFoundTest() {
        when(userRepository.findByIdAndIsActive(testUser.getId(), true)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(testUser.getId()));
    }
}
