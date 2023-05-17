package soma.everyonepick.api.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import soma.everyonepick.api.user.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .clientId("testClientId")
                .nickname("test")
                .thumbnailImageUrl("https://test.com/test.jpg")
                .build();
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void findByClientIdAndIsActiveTest() {
        Optional<User> found = userRepository.findByClientIdAndIsActive(user.getClientId(), true);

        assertTrue(found.isPresent());
        assertEquals(user.getClientId(), found.get().getClientId());
    }

    @Test
    public void findByIdAndIsActiveTest() {
        Optional<User> found = userRepository.findByIdAndIsActive(user.getId(), true);

        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
    }
}
