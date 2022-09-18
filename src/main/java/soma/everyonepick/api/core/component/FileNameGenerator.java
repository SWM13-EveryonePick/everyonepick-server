package soma.everyonepick.api.core.component;

public interface FileNameGenerator {
    /**
     * 파일 이름을 구현 규칙에 맞게 생성합니다.
     * @param originalFileName 파일 원본 이름
     * @return 생성된 파일 이름
     */
    String generate(String originalFileName);
}
