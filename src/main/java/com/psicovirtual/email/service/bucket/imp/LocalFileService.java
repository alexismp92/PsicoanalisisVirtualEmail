package com.psicovirtual.email.service.bucket.imp;

import com.psicovirtual.email.component.S3Properties;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static com.psicovirtual.email.utils.Utils.findFilesWithPrefix;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile({"local"})
public class LocalFileService implements IBucketOperations {

    private final S3Properties s3Properties;

    @Override
    public Set<File> download(Set<String> keys) {
        log.info("download files " + keys.toString());
        var files = new HashSet<File>();

        try{
            final var folderPath =
                    s3Properties.getImageFolder().endsWith(File.separator) ?
                            Path.of(s3Properties.getImageFolder()) : Path.of(s3Properties.getImageFolder() + File.separator);

            for(var key : keys){

                var filesFound = findFilesWithPrefix(folderPath.normalize(), key);

                if(filesFound.size() > 0){
                    var file = filesFound.get(0).toFile();
                    files.add(file);
                }else{
                    log.error("File not found: " + key);
                }

            }
        }catch (Exception e){
            log.error("Problem getting the resource: " + e.getMessage());
        }

        return files;
    }

    @Override
    public boolean isBucketExists(String bucketName) {
        return true;
    }
}
