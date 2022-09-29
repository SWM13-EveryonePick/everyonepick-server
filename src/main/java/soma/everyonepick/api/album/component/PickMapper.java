package soma.everyonepick.api.album.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.service.PickPhotoService;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PickMapper {
    @Autowired
    protected PickPhotoService pickPhotoService;
    @Autowired
    protected PhotoMapper photoMapper;

    @Mapping(target = "photo", expression = "java(getPhotoDto(pick))")
    public abstract PickDto.PickListDto toListDto(Pick pick);
    @Mapping(target = "photos", expression = "java(getPhotoDtos(pick))")
    public abstract PickDto.PickDetailDto toDetailDto(Pick pick);

    protected PhotoDto.PhotoResponseDto getPhotoDto(Pick pick) {
        List<Photo> photos = pickPhotoService.getPhotos(pick);
        return photoMapper.toDto(photos.get(0));
    }

    protected List<PhotoDto.PhotoResponseDto> getPhotoDtos(Pick pick) {
        return pickPhotoService.getPhotos(pick)
                .stream()
                .map(photoMapper::toDto)
                .collect(Collectors.toList());
    }
}
