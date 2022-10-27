package soma.everyonepick.api.user.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.user.dto.UserResponseDto;
import soma.everyonepick.api.user.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-28T00:54:12+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.id( user.getId() );
        userResponseDto.nickname( user.getNickname() );
        userResponseDto.clientId( user.getClientId() );
        userResponseDto.thumbnailImageUrl( user.getThumbnailImageUrl() );
        userResponseDto.isPushActive( user.getIsPushActive() );
        userResponseDto.isRegistered( user.getIsRegistered() );

        return userResponseDto.build();
    }
}
