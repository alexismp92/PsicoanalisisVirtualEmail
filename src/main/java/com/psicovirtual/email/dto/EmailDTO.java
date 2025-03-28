package com.psicovirtual.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDTO {
    @NotBlank
    @Email
    private String emailFrom;
    @NotEmpty
    private List<String> emails;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
