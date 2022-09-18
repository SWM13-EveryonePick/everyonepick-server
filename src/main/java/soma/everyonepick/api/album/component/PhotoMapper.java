package soma.everyonepick.api.album.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.entity.Photo;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PhotoMapper {
    PhotoDto.PhotoResponseDto toDto(Photo photo);
}
