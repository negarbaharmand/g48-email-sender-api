package se.lexicon.g48emailsenderapi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import se.lexicon.g48emailsenderapi.config.EmailProperties;
import se.lexicon.g48emailsenderapi.domain.dto.EmailDTO;
import se.lexicon.g48emailsenderapi.domain.entity.Email;
import se.lexicon.g48emailsenderapi.exception.EmailException;
import se.lexicon.g48emailsenderapi.repository.EmailRepository;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailProperties emailProperties;
    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(EmailProperties emailProperties, EmailRepository emailRepository1, JavaMailSender javaMailSender) {
        this.emailProperties = emailProperties;
        this.emailRepository = emailRepository1;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Void sendEmail(EmailDTO dto) {
        //1. Check parameter
        if (dto == null) throw new IllegalArgumentException("Email is null");

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            //2. Build entity and save to database
            mimeMessage.setContent(dto.getHtml(), "text/html");
            helper.setTo(dto.getTo());
            helper.setFrom(emailProperties.getUsername());
            helper.setSubject(dto.getSubject());


            Email entity = Email.builder()
                    .to(dto.getTo())
                    .from(emailProperties.getUsername())
                    .subject(dto.getSubject())
                    .content(dto.getHtml())
                    .build();

            emailRepository.save(entity);

            //3. Send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new EmailException("Error sending email: " + e.getMessage(), e);
        }
        return null;
    }
}
