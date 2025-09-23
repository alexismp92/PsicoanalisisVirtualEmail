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

import java.io.FileNotFoundException;

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
        var key = "static/EMAIL/EN/WELCOME_USER.HTML";

        ResponseInputStream<GetObjectResponse> objectResponse = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(objectResponse);
        when(s3Properties.getBucketName()).thenReturn("test-bucket");
        when(s3Properties.getImageFolder()).thenReturn("static");

        var downloadedFiles = awsBucketService.download(key);

        assertNotNull(downloadedFiles);
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testDownloadFileNotFoundException() {
        var key = "static/EMAIL/EN/WELCOME_USER.HTML";
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        assertThrows(FileNotFoundException.class, () -> awsBucketService.download(key));
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