package soma.everyonepick.api.pose.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.pose.dto.PoseDto;
import soma.everyonepick.api.pose.entity.Pose;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-28T00:54:12+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class PoseMapperImpl implements PoseMapper {

    @Override
    public PoseDto toDto(Pose pose) {
        if ( pose == null ) {
            return null;
        }

        PoseDto.PoseDtoBuilder poseDto = PoseDto.builder();

        poseDto.id( pose.getId() );
        poseDto.peopleNum( pose.getPeopleNum() );
        poseDto.poseUrl( pose.getPoseUrl() );

        return poseDto.build();
    }
}
