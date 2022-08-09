package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.GroupAlbumReadDto;
import soma.everyonepick.api.album.entity.GroupAlbum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-09T21:57:53+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class GroupAlbumMapperImpl extends GroupAlbumMapper {

    @Override
    public GroupAlbumReadDto toReadDto(GroupAlbum groupAlbum) {
        if ( groupAlbum == null ) {
            return null;
        }

        GroupAlbumReadDto.GroupAlbumReadDtoBuilder groupAlbumReadDto = GroupAlbumReadDto.builder();

        groupAlbumReadDto.id( groupAlbum.getId() );
        groupAlbumReadDto.title( groupAlbum.getTitle() );
        groupAlbumReadDto.hostUserId( groupAlbum.getHostUserId() );

        groupAlbumReadDto.users( getMemberDtos(groupAlbum) );
        groupAlbumReadDto.photos( getPhotoDtos(groupAlbum) );

        return groupAlbumReadDto.build();
    }
}
