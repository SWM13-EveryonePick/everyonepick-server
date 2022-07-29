package soma.everyonepick.api.user.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.user.dto.UserDto;
import soma.everyonepick.api.user.entity.User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
