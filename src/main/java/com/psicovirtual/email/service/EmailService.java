package com.psicovirtual.email.service;

import com.psicovirtual.email.dao.imp.EmailConfigService;
import com.psicovirtual.email.dto.EmailDTO;
import com.psicovirtual.email.dto.EmailRequestDTO;
import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.exception.NotFoundException;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.psicovirtual.email.utils.Constants.ADMIN_TYPE;
import static com.psicovirtual.email.utils.Constants.UTF_8;
import static com.psicovirtual.email.utils.Utils.extractImgIdentifiers;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfigService emailConfigService;
    private final IBucketOperations ibucketOperations;
    private final JavaMailSender sender;


    public void sendEmail(EmailRequestDTO emailRequestDTO) throws EmailException {
        try{
            var emailConfig = emailConfigService.getEmailByType(emailRequestDTO.getEmailType());
            List<String> emails = (emailConfig.getType().contains(ADMIN_TYPE)) ?  List.of(emailConfig.getEmailTo()): emailRequestDTO.getEmails().stream().toList();

            log.info("Sending email to: " + emails);
            log.info("From: " + emailConfig.getEmailFrom());
            log.info("Subject: " + emailConfig.getSubject());
            log.info("Message: " + emailConfig.getMessage());

            var emailDTO = EmailDTO.builder().emailFrom(emailConfig.getEmailFrom()).
                    emails(emails).subject(emailConfig.getSubject()).
                    message(emailConfig.getMessage()).
                    build();

            sendEmail(emailDTO);

        }catch(NotFoundException ex){
            log.error("Email type not found: " + ex.getMessage());
            throw new EmailException(ex.getMessage());
        }

    }


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

            var imgIdentifiers = extractImgIdentifiers(emailDTO.getMessage());
            helper.setText(emailDTO.getMessage(), true);

            var files = ibucketOperations.download(imgIdentifiers);

            for (var file : files) {
                helper.addAttachment(file.getName(), file);
            }

            sender.send(message);
            log.info("Email sent");

        }catch (MessagingException ex) {
            log.error("Error sending email: " + ex.getMessage());
            throw new EmailException(ex.getMessage());
        }
    }
}
