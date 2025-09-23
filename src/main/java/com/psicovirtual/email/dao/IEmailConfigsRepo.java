package com.psicovirtual.email.dao;

import com.psicovirtual.email.entities.EmailConfigs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IEmailConfigsRepo extends JpaRepository<EmailConfigs, Long> {

    @Transactional(readOnly = true)
    Optional<EmailConfigs> findByEmailTypes_EmailTypeAndLanguages_LanguageName(String type, String language);

}
