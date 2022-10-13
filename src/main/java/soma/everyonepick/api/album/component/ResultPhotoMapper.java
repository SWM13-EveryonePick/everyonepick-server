package soma.everyonepick.api.album.component;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.entity.ResultPhoto;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ResultPhotoMapper {
    ResultPhotoDto.ResultPhotoResponseDto toResponseDto(ResultPhoto resultphoto);
}
