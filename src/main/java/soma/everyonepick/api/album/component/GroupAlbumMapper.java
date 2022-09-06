package soma.everyonepick.api.album.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import soma.everyonepick.api.album.dto.GroupAlbumListResponseDto;
import soma.everyonepick.api.album.dto.GroupAlbumResponseDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.service.PhotoService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.user.component.UserMapper;
import soma.everyonepick.api.user.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class GroupAlbumMapper {
    @Autowired
    protected UserGroupAlbumService userGroupAlbumService;
    @Autowired
    protected PhotoService photoService;

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected PhotoMapper photoMapper;

    @Mapping(target = "users", expression = "java(getMemberDtos(groupAlbum))")
    public abstract GroupAlbumResponseDto toResponseDto(GroupAlbum groupAlbum);

    @Mapping(target = "users", expression = "java(getMemberDtos(groupAlbum))")
    @Mapping(target = "photoCnt", expression = "java(getPhotoCnt(groupAlbum))")
    public abstract GroupAlbumListResponseDto toListResponseDto(GroupAlbum groupAlbum);

    protected List<UserResponseDto> getMemberDtos(GroupAlbum groupAlbum) {
        return userGroupAlbumService.getMembers(groupAlbum).stream()
                .map(s -> userMapper.toDto(s)).collect(Collectors.toList());
    }

    protected Long getPhotoCnt(GroupAlbum groupAlbum) {
        return photoService.getPhotosByGroupAlbum(groupAlbum).stream().count();
    }
}
