package com.psicovirtual.email.dto;

import com.psicovirtual.email.utils.enums.EmailContentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDTO {
    private String emailFrom;
    private List<String> emails;
    private String subject;
    private String template;
    private HashMap<EmailContentEnum, String> values;
}
