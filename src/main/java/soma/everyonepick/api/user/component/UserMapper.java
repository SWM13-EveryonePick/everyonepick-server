package soma.everyonepick.api.user.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import soma.everyonepick.api.user.dto.UserRequestDto;
import soma.everyonepick.api.user.dto.UserResponseDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.UserService;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    protected UserService userService;
    public abstract UserResponseDto toDto(User user);
    public User toEntity(UserRequestDto userRequestDto) {
        String clientId = userRequestDto.getClientId();
        return userService.findByClientIdThrowsException(clientId);
    }
}
