package soma.everyonepick.api.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import soma.everyonepick.api.user.entity.DeviceToken;
import soma.everyonepick.api.user.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeviceTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    private DeviceToken deviceToken;
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .clientId("testClientId")
                .nickname("test")
                .thumbnailImageUrl("https://test.com/test.jpg")
                .build();

        deviceToken = DeviceToken.builder()
                .fcmDeviceToken("testToken")
                .user(user)
                .build();
        entityManager.persist(user);
        entityManager.persist(deviceToken);
        entityManager.flush();
    }

    @Test
    public void findByUserAndIsActiveTest() {
        DeviceToken found = deviceTokenRepository.findByUserAndIsActive(user, true).get();

        assertEquals(deviceToken.getFcmDeviceToken(), found.getFcmDeviceToken());
    }
}
