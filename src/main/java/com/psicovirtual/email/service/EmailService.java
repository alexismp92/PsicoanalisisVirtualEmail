package com.psicovirtual.email.service;

import com.psicovirtual.email.dto.EmailDTO;
import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.service.bucket.IBucketOperations;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import static com.psicovirtual.email.utils.Utils.extractImgIdentifiers;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final IBucketOperations ibucketOperations;
    private final JavaMailSender sender;
    public void sendEmail(EmailDTO emailDTO) throws EmailException {
        log.info("Sending email to: {}", emailDTO.getEmails());

        try{

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            var emailsArray = new InternetAddress[emailDTO.getEmails().size()];

            for (int i = 0; i < emailDTO.getEmails().size(); i++) {
                emailsArray[i] = new InternetAddress(emailDTO.getEmails().get(i));
            }

            helper.setFrom(emailDTO.getEmailFrom());
            helper.setTo(emailsArray);

            var imgIdentifiers = extractImgIdentifiers(emailDTO.getMessage());

            //helper.setText("<html><body><h1>Hello</h1><img src='cid:logo.jpg'></body></html>", true);
            helper.setText(emailDTO.getMessage(), true);

            //download the image from the S3 bucket
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
