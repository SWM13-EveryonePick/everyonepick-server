package soma.everyonepick.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.component.UserMapper;
import soma.everyonepick.api.user.dto.UserDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_USER;

@Validated
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * 사용자를 저장합니다.
     * @param user 저장하고자 하는 사용자 엔티티
     * @return 저장된 사용자 엔티티
     */
    @Transactional
    public UserDto saveUser(@Valid User user) {
        return userMapper.toDto(
                userRepository.saveAndFlush(user)
        );
    }

    /**
     * 사용자 프로필을 식별자로 조회합니다.
     * @param clientId 조회하고자 하는 사용자 회원아이디
     * @return 사용자 엔티티
     * @throws ResourceNotFoundException 존재하지 않는 회원아이디의 경우
     */
    @Transactional(readOnly = true)
    public UserDto findByClientId(String clientId) {
        return userMapper.toDto(
                userRepository.findByClientIdAndIsActive(clientId, true)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_USER))
        );
    }

    /**
     * 사용자 프로필을 조회합니다.
     * @param userId 조회하고자 하는 사용자 Id
     * @return 사용자 엔티티
     * @throws ResourceNotFoundException 존재하지 않는 사용자 Id의 경우
     */
    @Transactional(readOnly = true)
    public UserDto findById(Long userId) {
        return userMapper.toDto(
                userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_USER))
        );
    }
}
