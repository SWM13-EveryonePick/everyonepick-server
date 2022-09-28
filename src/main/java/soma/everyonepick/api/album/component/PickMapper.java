package soma.everyonepick.api.album.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.entity.Pick;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PickMapper {
    PickDto toDto(Pick pick);
}
