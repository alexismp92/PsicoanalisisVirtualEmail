package com.psicovirtual.email.service.bucket.imp;

import com.psicovirtual.email.component.S3Properties;
import com.psicovirtual.email.exception.EmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AWSBucketServiceTest {

    @Mock
    private S3Properties s3Properties;
    @Mock
    private S3Client s3Client;

    @InjectMocks
    private AWSBucketService awsBucketService;

    @Test
    public void testDownloadSuccess() throws Exception {
        Set<String> keys = Set.of("logo.jpg");

        ResponseInputStream<GetObjectResponse> objectResponse = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(objectResponse);
        when(s3Properties.getBucketName()).thenReturn("test-bucket");
        when(s3Properties.getImageFolder()).thenReturn("images");

        Set<File> downloadedFiles = awsBucketService.download(keys);

        assertEquals(1, downloadedFiles.size());
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testDownloadS3Exception() {
        Set<String> keys = Set.of("logo.jpg");
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        assertThrows(EmailException.class, () -> awsBucketService.download(keys));
    }

    @Test
    public void testIsBucketExists() {
        assertTrue(awsBucketService.isBucketExists("test-bucket"));
    }

    @Test
    public void testIsBucketNotExists() {
        when(s3Client.headBucket(any(HeadBucketRequest.class))).thenThrow(S3Exception.class);

        assertThrows(S3Exception.class, () -> awsBucketService.isBucketExists("test-bucket"));
    }
}