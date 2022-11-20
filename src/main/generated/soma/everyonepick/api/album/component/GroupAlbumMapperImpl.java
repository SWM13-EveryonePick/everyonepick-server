package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.GroupAlbumListResponseDto;
import soma.everyonepick.api.album.dto.GroupAlbumResponseDto;
import soma.everyonepick.api.album.entity.GroupAlbum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-19T19:59:03+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class GroupAlbumMapperImpl extends GroupAlbumMapper {

    @Override
    public GroupAlbumResponseDto toResponseDto(GroupAlbum groupAlbum) {
        if ( groupAlbum == null ) {
            return null;
        }

        GroupAlbumResponseDto.GroupAlbumResponseDtoBuilder groupAlbumResponseDto = GroupAlbumResponseDto.builder();

        groupAlbumResponseDto.id( groupAlbum.getId() );
        groupAlbumResponseDto.title( groupAlbum.getTitle() );
        groupAlbumResponseDto.hostUserId( groupAlbum.getHostUserId() );

        groupAlbumResponseDto.users( getMemberDtos(groupAlbum) );

        return groupAlbumResponseDto.build();
    }

    @Override
    public GroupAlbumListResponseDto toListResponseDto(GroupAlbum groupAlbum) {
        if ( groupAlbum == null ) {
            return null;
        }

        GroupAlbumListResponseDto.GroupAlbumListResponseDtoBuilder groupAlbumListResponseDto = GroupAlbumListResponseDto.builder();

        groupAlbumListResponseDto.id( groupAlbum.getId() );
        groupAlbumListResponseDto.title( groupAlbum.getTitle() );
        groupAlbumListResponseDto.hostUserId( groupAlbum.getHostUserId() );

        groupAlbumListResponseDto.users( getMemberDtos(groupAlbum) );
        groupAlbumListResponseDto.photoCnt( getPhotoCnt(groupAlbum) );

        return groupAlbumListResponseDto.build();
    }
}
