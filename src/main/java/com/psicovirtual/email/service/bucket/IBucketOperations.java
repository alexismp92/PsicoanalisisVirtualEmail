package com.psicovirtual.email.service.bucket;

import com.psicovirtual.email.exception.EmailException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static com.psicovirtual.email.utils.Constants.JAVA_IO_TMPDIR;

public interface IBucketOperations {

    /**
     * Download files from the bucket
     *
     * @param keys
     * @return
     * @throws EmailException
     */
    Set<File> download(Set<String> keys) throws EmailException;

    boolean isBucketExists(String bucketName);

    /**
     * Generate a temporary file
     *
     * @param file
     * @param uuid
     * @return
     * @throws IOException
     */
    default Path generateTmpFile(MultipartFile file, String uuid) throws IOException {
        var tmpFolder = System.getProperty(JAVA_IO_TMPDIR);
        Path dirPath = Path.of(tmpFolder, uuid);
        Files.createDirectories(dirPath);
        Path filePath = dirPath.resolve(file.getOriginalFilename());
        Files.createFile(filePath);
        file.transferTo(filePath);
        return filePath;
    }

}
