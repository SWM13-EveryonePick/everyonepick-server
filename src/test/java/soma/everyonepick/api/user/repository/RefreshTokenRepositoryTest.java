package soma.everyonepick.api.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import soma.everyonepick.api.user.entity.RefreshToken;
import soma.everyonepick.api.user.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RefreshTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken refreshToken;
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .clientId("testClientId")
                .nickname("test")
                .thumbnailImageUrl("https://test.com/test.jpg")
                .build();

        refreshToken = RefreshToken.builder()
                .user(user)
                .value("testRefreshToken")
                .build();
        entityManager.persist(user);
        entityManager.persist(refreshToken);
        entityManager.flush();
    }

    @Test
    public void findByUserTest() {
        RefreshToken found = refreshTokenRepository.findByUser(user).get();

        assertEquals(refreshToken.getValue(), found.getValue());
    }
}
