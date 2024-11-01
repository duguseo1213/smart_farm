package com.ssafy.WeCanDoFarm.server.domain.timeLapse.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.AddTimeLapsePictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.GetTimeLapsePicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.repository.TimeLapsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeLapseServiceImpl implements TimeLapseService {
    private final AmazonS3 amazonS3;
    private final TimeLapsRepository timeLapsRepository;
    private final GardenRepository gardenRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String upload(MultipartFile image) throws Exception {
        //입력받은 이미지 파일이 빈 파일인지 검증
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new BaseException(ErrorCode.EMPTY_FILE);
        }
        //uploadImage를 호출하여 S3에 저장된 이미지의 public url을 반환한다.
        return this.uploadImage(image);
    }

    @Override
    public String uploadImage(MultipartFile image) throws Exception {
        this.validateImageFileExtention(image.getOriginalFilename());
        return this.uploadImageToS3(image);

    }

    @Override
    public void validateImageFileExtention(String filename) throws Exception {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(ErrorCode.NOT_SUPPORTED_EXTENSION);
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new BaseException(ErrorCode.NOT_SUPPORTED_EXTENSION);
        }
    }

    @Override
    public String uploadImageToS3(MultipartFile image) throws Exception {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);
        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);


        //S3로 putObject 할 때 사용할 요청 객체
        //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);

        //실제로 S3에 이미지 데이터를 넣는 부분이다.
        amazonS3.putObject(putObjectRequest); // put image to S3


        byteArrayInputStream.close();
        is.close();


        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    @Override
    public void addTimeLapsePicture(AddTimeLapsePictureRequest request) throws Exception {
        String filePath=upload(request.getImage());
        Garden garden =gardenRepository.findById(request.getGardenId()).orElseThrow();
        TimeLapse tl = TimeLapse.create(garden,filePath);
        timeLapsRepository.save(tl);
    }

    @Override
    public List<GetTimeLapsePicturesResponse> getTimeLapses(Long gardenId) throws Exception {
        List<TimeLapse> timeLapse = timeLapsRepository.findByGardenId(gardenId);
        List<GetTimeLapsePicturesResponse> getTimeLapsePicturesResponses = new ArrayList<>();
        for(TimeLapse tl : timeLapse){
            GetTimeLapsePicturesResponse response = new GetTimeLapsePicturesResponse();
            response.setCreatedDate(tl.getCreatedDate());
            response.setImage(tl.getTimeLapseImage());
            getTimeLapsePicturesResponses.add(response);
        }
        return getTimeLapsePicturesResponses;
    }
}
