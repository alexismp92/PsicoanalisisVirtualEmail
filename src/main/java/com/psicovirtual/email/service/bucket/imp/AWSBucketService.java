package com.psicovirtual.email.service.bucket.imp;

import com.psicovirtual.email.component.S3Properties;
import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import static com.psicovirtual.email.utils.Constants.S3;
import static com.psicovirtual.email.utils.Constants.SLASH;
import static com.psicovirtual.email.utils.Utils.getExtension;
import static org.springdoc.core.utils.Constants.DOT;


@Service
@AllArgsConstructor
@Slf4j
@Profile("!local")
public class AWSBucketService implements IBucketOperations {

    private final S3Properties s3Properties;
    private final S3Client s3Client;

    @Override
    public Set<File> download(Set<String> keys) throws EmailException {
        Set<File> downloadedFiles = new HashSet<>();
        try{
            isBucketExists(s3Properties.getBucketName());

            for (String key : keys) {
                final var extension = getExtension(key);

                var getObjectResponse = s3Client.getObject(GetObjectRequest.builder()
                        .bucket(s3Properties.getBucketName())
                        .key(String.format(SLASH, s3Properties.getImageFolder(),key))
                        .build());

                var file = Files.createTempFile(S3, String.join(DOT,extension));
                Files.copy(getObjectResponse, file, StandardCopyOption.REPLACE_EXISTING);
                log.info("File downloaded to: " + file);
                downloadedFiles.add(file.toFile());
            }

        }catch (IOException | S3Exception e){
            log.error("Download failed: " + e.getMessage());
            throw new EmailException(e.getMessage());
        }
        return downloadedFiles;
    }


    @Override
    public boolean isBucketExists(String bucketName) throws S3Exception{
        try {
            log.info("Checking if bucket exists: " + bucketName);
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            return true;
        } catch (S3Exception e) {
            log.error("Bucket does not exist: " + e.getMessage());
            throw e;
        }
    }
}
