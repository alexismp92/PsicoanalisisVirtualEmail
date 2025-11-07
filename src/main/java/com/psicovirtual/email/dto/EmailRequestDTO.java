package com.psicovirtual.email.dto;

import com.psicovirtual.email.utils.enums.EmailContentEnum;
import com.psicovirtual.email.utils.enums.EmailTypeEnum;
import com.psicovirtual.email.utils.enums.LanguageEnum;

import lombok.Data;

import java.util.HashMap;
import java.util.Set;

@Data
public class EmailRequestDTO {

    private EmailTypeEnum emailType;
    private LanguageEnum language;
    private Set<String> emails;
    private HashMap<EmailContentEnum, String> values;

}
