package soma.everyonepick.api.user.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.entity.DeviceToken;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-18T21:54:37+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class DeviceTokenMapperImpl implements DeviceTokenMapper {

    @Override
    public DeviceTokenDto toDto(DeviceToken deviceToken) {
        if ( deviceToken == null ) {
            return null;
        }

        DeviceTokenDto.DeviceTokenDtoBuilder deviceTokenDto = DeviceTokenDto.builder();

        deviceTokenDto.fcmDeviceToken( deviceToken.getFcmDeviceToken() );

        return deviceTokenDto.build();
    }
}
