package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.entity.Photo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-19T19:59:04+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class PhotoMapperImpl implements PhotoMapper {

    @Override
    public PhotoDto.PhotoResponseDto toDto(Photo photo) {
        if ( photo == null ) {
            return null;
        }

        PhotoDto.PhotoResponseDto.PhotoResponseDtoBuilder<?, ?> photoResponseDto = PhotoDto.PhotoResponseDto.builder();

        photoResponseDto.id( photo.getId() );
        photoResponseDto.photoUrl( photo.getPhotoUrl() );

        return photoResponseDto.build();
    }
}
