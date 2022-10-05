package soma.everyonepick.api.album.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "picked-user")
public class PickInfoUser {
    @Id
    String pickId;
    List<Long> userIds;

    @TimeToLive
    Long timeOut;
}
