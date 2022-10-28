package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.entity.ResultPhoto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-28T00:54:12+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class ResultPhotoMapperImpl implements ResultPhotoMapper {

    @Override
    public ResultPhotoDto.ResultPhotoResponseDto toResponseDto(ResultPhoto resultphoto) {
        if ( resultphoto == null ) {
            return null;
        }

        ResultPhotoDto.ResultPhotoResponseDto.ResultPhotoResponseDtoBuilder<?, ?> resultPhotoResponseDto = ResultPhotoDto.ResultPhotoResponseDto.builder();

        resultPhotoResponseDto.id( resultphoto.getId() );
        resultPhotoResponseDto.resultPhotoUrl( resultphoto.getResultPhotoUrl() );

        return resultPhotoResponseDto.build();
    }
}
