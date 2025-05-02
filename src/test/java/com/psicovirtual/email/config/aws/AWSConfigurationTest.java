package com.psicovirtual.email.config.aws;

import com.psicovirtual.email.component.S3Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.exception.SdkException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AWSConfigurationTest {

    @Mock
    private AWSConfiguration awsConfiguration;
    @Test
    void s3ClientTest(){
        S3Properties s3Properties = new S3Properties();
        s3Properties.setRegion("us-east-1");
        s3Properties.setBucketName("bucketName");
        s3Properties.setKmsKey("kmsKey");
        s3Properties.setImageFolder("imageFolder");
        assertDoesNotThrow(() -> awsConfiguration.s3Client(s3Properties));
    }

}