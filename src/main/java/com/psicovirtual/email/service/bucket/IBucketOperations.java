package com.psicovirtual.email.service.bucket;

import com.psicovirtual.email.exception.EmailException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static com.psicovirtual.email.utils.Constants.JAVA_IO_TMPDIR;

public interface IBucketOperations {

    /** Download a file from the bucket
     *
     * @param key file key to download
     * @return Downloaded file
     * @throws EmailException if there is an error during the download process
     */
    File download(String key) throws FileNotFoundException;

    /** Verify if bucket exists
     *
     * @param bucketName bucket name to check
     * @return true if the bucket exists, false otherwise
     */
    boolean isBucketExists(String bucketName);

}
