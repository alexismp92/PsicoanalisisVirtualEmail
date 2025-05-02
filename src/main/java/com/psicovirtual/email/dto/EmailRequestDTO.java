package com.psicovirtual.email.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequestDTO {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z_]+$", message = "email type must contain only letters and underscores")
    @Size(min = 1, max = 100, message = "email type must be between 1 and 250 characters")
    private String emailType;
    private Set<String> emails;

}
