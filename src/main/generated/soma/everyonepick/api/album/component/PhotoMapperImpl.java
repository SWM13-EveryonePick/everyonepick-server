package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.entity.Photo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-09T21:57:53+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class PhotoMapperImpl implements PhotoMapper {

    @Override
    public PhotoDto toDto(Photo photo) {
        if ( photo == null ) {
            return null;
        }

        PhotoDto.PhotoDtoBuilder photoDto = PhotoDto.builder();

        photoDto.id( photo.getId() );
        photoDto.photoUrl( photo.getPhotoUrl() );

        return photoDto.build();
    }
}
