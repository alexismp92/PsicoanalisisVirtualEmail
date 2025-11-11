package com.psicovirtual.email.dao.repository;

import com.psicovirtual.email.entities.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IEmailConfigRepo extends JpaRepository<EmailConfig, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT e FROM EmailConfig e WHERE e.emailType.emailType = ?1 AND e.language.languageName = ?2")
    Optional<EmailConfig> findByEmailTypeAndLanguageName (String type, String language);

}
