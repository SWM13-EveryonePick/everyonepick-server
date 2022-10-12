package soma.everyonepick.api.pose.component;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import soma.everyonepick.api.pose.dto.PoseDto;
import soma.everyonepick.api.pose.entity.Pose;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PoseMapper {
    PoseDto toDto(Pose pose);
}
