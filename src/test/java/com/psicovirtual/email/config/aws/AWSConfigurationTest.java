package com.psicovirtual.email.config.aws;

import com.psicovirtual.email.component.S3Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.exception.SdkException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AWSConfigurationTest {

    private AWSConfiguration awsConfiguration;

    @BeforeEach
    void setUp() {
        awsConfiguration = new AWSConfiguration();
    }

    @Test
    void s3Client_Test(){
        S3Properties s3Properties = new S3Properties();
        s3Properties.setRegion("us-east-1");
        s3Properties.setBucketName("bucketName");
        s3Properties.setKmsKey("kmsKey");
        s3Properties.setImageFolder("imageFolder");
        assertThrows(SdkException.class, () -> awsConfiguration.s3Client(s3Properties));
    }

}