package soma.everyonepick.api.core.component;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    /**
     * 멀티파트 파일을 업로드합니다.
     * @param mFile 업로드 될 파일
     * @param saveFilePath 파일 경로
     * @return 다운로드 가능한 URL
     */
    String uploadMultiPartFile(MultipartFile mFile, String saveFilePath);
}
