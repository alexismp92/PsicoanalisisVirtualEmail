package com.psicovirtual.email.service;

import com.psicovirtual.email.component.EmailProperties;
import com.psicovirtual.email.dao.imp.EmailConfigService;
import com.psicovirtual.email.dto.EmailDTO;
import com.psicovirtual.email.dto.EmailRequestDTO;
import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.exception.NotFoundException;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import com.psicovirtual.email.utils.enums.EmailContentEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static com.psicovirtual.email.utils.Constants.*;
import static com.psicovirtual.email.utils.Utils.replacePlaceHolders;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfigService emailConfigService;
    private final IBucketOperations ibucketOperations;
    private final JavaMailSender sender;
    private final EmailProperties emailProperties;


    /** * Send an email based on the email type and language
     * @param emailRequestDTO
     * @throws EmailException
     */
    public void sendEmail(EmailRequestDTO emailRequestDTO) throws EmailException {
        try{
            var emailConfig = emailConfigService.getEmailByTypeAndLanguage(
                    emailRequestDTO.getEmailType().name(), emailRequestDTO.getLanguage().name());
            List<String> emails = emailRequestDTO.getEmails().stream().toList();

            if(emailConfig.getEmailTo() != null){
                emails.addAll(List.of(emailConfig.getEmailTo().split(COMMA)));
            }


            log.info("Sending email to: " + emails);
            log.info("From: " + emailConfig.getEmailFrom());
            log.info("Subject: " + emailConfig.getSubject());
            log.info("Template: " + emailConfig.getFilePath());

            var emailDTO = EmailDTO.builder().emailFrom(emailConfig.getEmailFrom()).
                    emails(emails).subject(emailConfig.getSubject()).
                    template(emailConfig.getFilePath()).values(emailRequestDTO.getValues()).
                    build();

            sendEmail(emailDTO);

        }catch(NotFoundException ex){
            log.error("Email type not found: " + ex.getMessage());
            throw new EmailException(ex.getMessage());
        }

    }


    /** * Send an email
     * @param emailDTO
     * @throws EmailException
     */
    public void sendEmail(EmailDTO emailDTO) throws EmailException {
        log.info("Sending email to: {}", emailDTO.getEmails());

        try{

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);

            var emailsArray = new InternetAddress[emailDTO.getEmails().size()];

            for (int i = 0; i < emailDTO.getEmails().size(); i++) {
                emailsArray[i] = new InternetAddress(emailDTO.getEmails().get(i));
            }

            helper.setSubject(emailDTO.getSubject());
            helper.setFrom(emailDTO.getEmailFrom());
            helper.setTo(emailsArray);

            var file = ibucketOperations.download(emailDTO.getTemplate());
            var html = new String(Files.readAllBytes(file.toPath()));

            addDefaultEmailCfgValues(emailDTO);

            var emailHtml  = replacePlaceHolders(html, emailDTO.getValues());

            helper.setText(emailHtml, true);

            log.debug("Email HTML: " + emailHtml);
            sender.send(message);
            log.info("Email sent");

        }catch (MessagingException ex) {
            log.error("Error sending email: " + ex.getMessage());
            throw new EmailException(ex.getMessage());
        } catch (IOException e) {
            throw new EmailException("Error reading the file: " + e.getMessage());
        }
    }

    /** * Add default values to the email if not present
     * @param emailDTO
     */
    private void addDefaultEmailCfgValues(EmailDTO emailDTO) {
        if(!emailDTO.getValues().containsKey(EmailContentEnum.COMPANY)){
            emailDTO.getValues().put(EmailContentEnum.COMPANY, emailProperties.getCompany());
        }

        if(!emailDTO.getValues().containsKey(EmailContentEnum.ADDRESS)){
            emailDTO.getValues().put(EmailContentEnum.ADDRESS, emailProperties.getAddress());
        }

        if(!emailDTO.getValues().containsKey(EmailContentEnum.LOGO)){
            emailDTO.getValues().put(EmailContentEnum.LOGO, String.valueOf(emailProperties.getLogo()));
        }

        if(!emailDTO.getValues().containsKey(EmailContentEnum.LOGIN)){
            emailDTO.getValues().put(EmailContentEnum.LOGIN, String.valueOf(emailProperties.getLoginUrl()));
        }

        if(!emailDTO.getValues().containsKey(EmailContentEnum.SUPPORT)){
            emailDTO.getValues().put(EmailContentEnum.SUPPORT, String.valueOf(emailProperties.getSupportUrl()));
        }

        if(!emailDTO.getValues().containsKey(EmailContentEnum.YEAR)){
            emailDTO.getValues().put(EmailContentEnum.YEAR, String.valueOf(LocalDate.now().getYear()));
        }
    }

}
