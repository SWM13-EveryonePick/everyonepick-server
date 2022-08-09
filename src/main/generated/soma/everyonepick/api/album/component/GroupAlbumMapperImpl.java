package soma.everyonepick.api.album.component;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.GroupAlbumReadDetailDto;
import soma.everyonepick.api.album.dto.GroupAlbumReadListDto;
import soma.everyonepick.api.album.entity.GroupAlbum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-10T01:12:48+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class GroupAlbumMapperImpl extends GroupAlbumMapper {

    @Override
    public GroupAlbumReadDetailDto toReadDetailDto(GroupAlbum groupAlbum) {
        if ( groupAlbum == null ) {
            return null;
        }

        GroupAlbumReadDetailDto.GroupAlbumReadDetailDtoBuilder groupAlbumReadDetailDto = GroupAlbumReadDetailDto.builder();

        groupAlbumReadDetailDto.id( groupAlbum.getId() );
        groupAlbumReadDetailDto.title( groupAlbum.getTitle() );
        groupAlbumReadDetailDto.hostUserId( groupAlbum.getHostUserId() );

        groupAlbumReadDetailDto.users( getMemberDtos(groupAlbum) );
        groupAlbumReadDetailDto.photos( getPhotoDtos(groupAlbum) );

        return groupAlbumReadDetailDto.build();
    }

    @Override
    public GroupAlbumReadListDto toReadListDto(GroupAlbum groupAlbum) {
        if ( groupAlbum == null ) {
            return null;
        }

        GroupAlbumReadListDto.GroupAlbumReadListDtoBuilder groupAlbumReadListDto = GroupAlbumReadListDto.builder();

        groupAlbumReadListDto.id( groupAlbum.getId() );
        groupAlbumReadListDto.title( groupAlbum.getTitle() );
        groupAlbumReadListDto.hostUserId( groupAlbum.getHostUserId() );

        groupAlbumReadListDto.users( getMemberDtos(groupAlbum) );
        groupAlbumReadListDto.photoCnt( getPhotoCnt(groupAlbum) );

        return groupAlbumReadListDto.build();
    }
}
