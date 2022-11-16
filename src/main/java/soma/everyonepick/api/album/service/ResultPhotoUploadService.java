package soma.everyonepick.api.album.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.ResultPhoto;
import soma.everyonepick.api.album.repository.ResultPhotoRepository;
import soma.everyonepick.api.core.component.FileNameGenerator;
import soma.everyonepick.api.core.component.FileUploader;
import soma.everyonepick.api.core.util.PathUtil;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultPhotoUploadService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private static final String DASH = "/";
    private static final String CDN_URL = "https://dosf6do8h1hli.cloudfront.net";
    private static final String IMAGE_DIR = "images";
    private final FileNameGenerator fileNameGenerator;
    private final ResultPhotoRepository resultPhotoRepository;

    /**
     * 합성결과 사진을 S3에 업로드하고 ResultPhoto를 저장.
     * @param image 합성결과 사진
     * @param pick 사진선택 작업 엔티티
     * @return ResultPhoto 합성결과 엔티티
     */
    @Transactional
    public ResultPhoto uploadResultPhoto(ByteArrayInputStream image, Pick pick) {
        GroupAlbum groupAlbum = pick.getGroupAlbum();
        final String ALBUM_ID = groupAlbum.getTitle() + '_' + groupAlbum.getId().toString();
        String generatedFileName = fileNameGenerator.generate(pick.getId().toString());
        generatedFileName = IMAGE_DIR + DASH + ALBUM_ID + '_'+ "result" + DASH + generatedFileName;

        String fullFilePath = PathUtil.replaceWindowPathToLinuxPath(generatedFileName);
        log.info("fullFilePath: " + fullFilePath);

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(Mimetypes.MIMETYPE_OCTET_STREAM);
        PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(
                bucket, fullFilePath, image, objMeta
        ).withBucketName(bucket));
        log.info("ContentMd5: " + putObjectResult.getContentMd5());

        String downloadableUrl = CDN_URL + DASH + fullFilePath;
        log.info("URL: " + downloadableUrl);

        return resultPhotoRepository.saveAndFlush(
                ResultPhoto.builder()
                        .groupAlbum(groupAlbum)
                        .resultPhotoUrl(downloadableUrl)
                        .build()
        );
    }
}
