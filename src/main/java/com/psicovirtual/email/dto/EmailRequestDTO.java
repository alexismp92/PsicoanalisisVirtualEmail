package com.psicovirtual.email.dto;

import com.psicovirtual.email.utils.enums.EmailContentEnum;
import com.psicovirtual.email.utils.enums.EmailTypeEnum;
import com.psicovirtual.email.utils.enums.LanguageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequestDTO {

    private EmailTypeEnum emailType;
    private LanguageEnum language;
    private Set<String> emails;
    private HashMap<EmailContentEnum, String> values;

}
