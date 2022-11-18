package soma.everyonepick.api.user.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.entity.DeviceToken;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DeviceTokenMapper {
    DeviceTokenDto toDto(DeviceToken deviceToken);
}
