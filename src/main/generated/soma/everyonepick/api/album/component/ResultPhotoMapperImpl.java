package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.entity.ResultPhoto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-12T02:01:02+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class ResultPhotoMapperImpl implements ResultPhotoMapper {

    @Override
    public ResultPhotoDto toDto(ResultPhoto resultphoto) {
        if ( resultphoto == null ) {
            return null;
        }

        ResultPhotoDto.ResultPhotoDtoBuilder resultPhotoDto = ResultPhotoDto.builder();

        resultPhotoDto.id( resultphoto.getId() );
        resultPhotoDto.resultPhotoUrl( resultphoto.getResultPhotoUrl() );

        return resultPhotoDto.build();
    }
}
