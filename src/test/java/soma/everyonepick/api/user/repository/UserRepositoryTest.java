package soma.everyonepick.api.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:sql/user.sql"})
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원아이디와 활성화 여부로 유저 검색")
    void findByClientIdAndIsActive() {
        User user = userRepository.findByClientIdAndIsActive("kakao_1", true).get();
        assertEquals(1L, user.getId());
        assertEquals("kakao_1", user.getClientId());
        assertEquals("영훈", user.getNickname());
        assertEquals("http://kakao_image1.com", user.getThumbnailImageUrl());
    }

    @Test
    @DisplayName("id와 활성화 여부로 유저 검색")
    void findByIdAndIsActive() {
        User user = userRepository.findByIdAndIsActive(2L, true).get();
        assertEquals(2L, user.getId());
        assertEquals("kakao_2", user.getClientId());
        assertEquals("한빛", user.getNickname());
        assertEquals("http://kakao_image2.com", user.getThumbnailImageUrl());
    }
}