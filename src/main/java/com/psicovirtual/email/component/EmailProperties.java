package com.psicovirtual.email.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mail.cfg")
public class EmailProperties {
    private String company;
    private String address;
    private String logo;
}
