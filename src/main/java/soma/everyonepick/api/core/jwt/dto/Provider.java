package soma.everyonepick.api.core.jwt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;
import soma.everyonepick.api.core.exception.BadRequestException;

import java.util.stream.Stream;

public enum Provider {
    KAKAO;

    @JsonCreator
    public static Provider create(String requestValue) {
        if (! StringUtils.hasLength(requestValue)) {
            return Provider.KAKAO;
        }

        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("잘못된 프로바이더입니다."));
    }
}
