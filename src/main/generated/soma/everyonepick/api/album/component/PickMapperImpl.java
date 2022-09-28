package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.entity.Pick;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T03:08:19+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class PickMapperImpl implements PickMapper {

    @Override
    public PickDto toDto(Pick pick) {
        if ( pick == null ) {
            return null;
        }

        PickDto.PickDtoBuilder pickDto = PickDto.builder();

        pickDto.id( pick.getId() );
        pickDto.isDone( pick.getIsDone() );
        pickDto.expired_at( pick.getExpired_at() );

        return pickDto.build();
    }
}
