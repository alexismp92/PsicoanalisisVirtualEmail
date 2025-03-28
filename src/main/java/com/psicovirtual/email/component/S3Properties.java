package com.psicovirtual.email.component;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class S3Properties {
    private String bucketName;
    private String region;
    private String kmsKey;
    private String imageFolder;
}
