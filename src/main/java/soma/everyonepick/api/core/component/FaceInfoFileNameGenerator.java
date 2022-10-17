package soma.everyonepick.api.core.component;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("faceInfoFileNameGenerator")
public class FaceInfoFileNameGenerator implements FileNameGenerator{
    private static final String UNDER_DASH = "_";

    /**
     * 파일 이름을 구현 규칙에 맞게 생성합니다.
     * @param originalFileName 파일 원본 이름
     * @return 생성된 파일 이름
     */
    @Override
    public String generate(String originalFileName) {
        return "face" + UNDER_DASH + UUID.randomUUID();
    }
}
