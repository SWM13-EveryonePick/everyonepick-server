package soma.everyonepick.api.user.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.user.dto.UserDto;
import soma.everyonepick.api.user.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-04T04:28:27+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.nickname( user.getNickname() );
        userDto.clientId( user.getClientId() );
        userDto.thumbnailImageUrl( user.getThumbnailImageUrl() );
        userDto.isPushActive( user.getIsPushActive() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.nickname( userDto.getNickname() );
        user.clientId( userDto.getClientId() );
        user.thumbnailImageUrl( userDto.getThumbnailImageUrl() );
        user.isPushActive( userDto.getIsPushActive() );

        return user.build();
    }
}
