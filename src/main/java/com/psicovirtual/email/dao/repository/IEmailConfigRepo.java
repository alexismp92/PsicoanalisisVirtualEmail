package com.psicovirtual.email.dao.repository;

import com.psicovirtual.email.entities.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IEmailConfigRepo extends JpaRepository<EmailConfig, Long> {

    @Transactional(readOnly = true)
    Optional<EmailConfig> findByEmailType_EmailTypeAndLanguage_LanguageName (String type, String language);

}
