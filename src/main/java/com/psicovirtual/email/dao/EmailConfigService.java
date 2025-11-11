package com.psicovirtual.email.dao;

import com.psicovirtual.email.dao.repository.IEmailConfigRepo;
import com.psicovirtual.email.entities.EmailConfig;
import com.psicovirtual.email.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConfigService {

    private final IEmailConfigRepo iEmailConfigRepo;


    /**     * Get email configuration by type and language.
     *
     * @param type     the type of email configuration
     * @param language the language of email configuration
     * @return the email configuration
     * @throws NotFoundException if the email configuration is not found
     */
    public EmailConfig getEmailByTypeAndLanguage(String type, String language) throws NotFoundException {
        log.info("looking for email type %s and language %s", type, language);
        return this.iEmailConfigRepo.findByEmailTypeAndLanguageName(type, language).orElseThrow(() -> new NotFoundException("Email type " + type + " and language " + language + " not found"));
    }

}
