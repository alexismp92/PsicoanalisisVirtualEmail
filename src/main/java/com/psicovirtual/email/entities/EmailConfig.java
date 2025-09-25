package com.psicovirtual.email.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "EMAIL_CONFIGS",
        indexes = {
        @Index(name = "IDX_EMAIL_CONFIGS_01", columnList = "emailCfgId,emailTypeId,emailLanguageId"),
        @Index(name = "IDX_EMAIL_CONFIGS_02", columnList = "emailTypeId,emailLanguageId"),
})
public class EmailConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_config_seq")
    @SequenceGenerator(name = "email_config_seq", sequenceName = "email_configs_sequence", allocationSize = 1)
    private Long emailCfgId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emailTypeId", nullable = false)
    private EmailType emailType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "languageId", nullable = false)
    private Language language;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String filePath;
}