package com.ssafy.WeCanDoFarm.server.domain.S3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String upload(MultipartFile file) throws Exception {
        //입력받은 이미지 파일이 빈 파일인지 검증
        if(file.isEmpty() || Objects.isNull(file.getOriginalFilename())){
            throw new BaseException(ErrorCode.EMPTY_FILE);
        }
        //uploadImage를 호출하여 S3에 저장된 이미지의 public url을 반환한다.
        return this.uploadFile(file);
    }


    public String uploadFile(MultipartFile file) throws Exception {
        this.validateFileExtension(file.getOriginalFilename());
        return this.uploadFileToS3(file);

    }


    public void validateFileExtension(String filename) throws Exception {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(ErrorCode.NOT_SUPPORTED_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif", "mp4", "mov", "avi", "mkv");;

        if (!allowedExtentionList.contains(extension)) {
            throw new BaseException(ErrorCode.NOT_SUPPORTED_EXTENSION);
        }
    }


    public String uploadFileToS3(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename(); //원본 파일 명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename; //변경된 파일 명


        //file을 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentLength(file.getSize());
        // 확장자에 따라 ContentType 설정
        String contentType;
        switch (extension) {
            case "mp4":
                contentType = "video/mp4";
                break;
            case "mov":
                contentType = "video/quicktime";
                break;
            case "avi":
                contentType = "video/x-msvideo";
                break;
            case "mkv":
                contentType = "video/x-matroska";
                break;
            default:
                contentType = "image/" + extension;
        }
        metadata.setContentType(contentType);



        // InputStream을 직접 PutObjectRequest에 전달
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            // S3에 파일 업로드
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

}
