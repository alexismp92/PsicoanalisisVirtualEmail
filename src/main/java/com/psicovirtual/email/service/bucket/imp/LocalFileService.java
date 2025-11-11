package com.psicovirtual.email.service.bucket.imp;

import com.psicovirtual.email.component.S3Properties;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile({"!uat", "!prod"})
public class LocalFileService implements IBucketOperations {

    private final S3Properties s3Properties;

    @Override
    public File download(String key){
        log.info("download file " + key);
        File file = null;

        try{

            var filePath = Paths.get(String.format("%s/%s", s3Properties.getImageFolder(), key)).normalize();
            log.info("File Path: " + filePath);
            var resource = new ClassPathResource(filePath.toString());

            if(resource.exists()){
                file = resource.getFile();
            }else{
                log.error("File not found: " + key);
            }

        }catch (Exception e){
            log.error("Problem getting the resource: " + e.getMessage());
        }

        return file;
    }

    @Override
    public boolean isBucketExists(String bucketName) {
        return true;
    }
}
