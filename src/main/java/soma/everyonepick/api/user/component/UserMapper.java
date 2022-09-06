package soma.everyonepick.api.user.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.user.dto.UserResponseDto;
import soma.everyonepick.api.user.entity.User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity(UserResponseDto userResponseDto);
}
