package soma.everyonepick.api.album.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "picked-photo")
public class PickInfoPhoto {
    @Id
    String userPickId;

    List<Long> photoIds;
}
