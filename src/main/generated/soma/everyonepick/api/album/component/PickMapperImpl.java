package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.entity.Pick;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-07T15:18:09+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class PickMapperImpl extends PickMapper {

    @Override
    public PickDto.PickListDto toListDto(Pick pick) {
        if ( pick == null ) {
            return null;
        }

        PickDto.PickListDto.PickListDtoBuilder<?, ?> pickListDto = PickDto.PickListDto.builder();

        pickListDto.id( pick.getId() );

        pickListDto.photo( getPhotoDto(pick) );

        return pickListDto.build();
    }

    @Override
    public PickDto.PickDetailDto toDetailDto(Pick pick) {
        if ( pick == null ) {
            return null;
        }

        PickDto.PickDetailDto.PickDetailDtoBuilder<?, ?> pickDetailDto = PickDto.PickDetailDto.builder();

        pickDetailDto.id( pick.getId() );

        pickDetailDto.photos( getPhotoDtos(pick) );

        return pickDetailDto.build();
    }
}
