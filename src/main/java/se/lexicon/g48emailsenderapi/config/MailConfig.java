package se.lexicon.g48emailsenderapi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    private final EmailProperties emailProperties;

    @Autowired
    public MailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        //craete an instance of JavaMailSenderImpl

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());
        mailSender.getJavaMailProperties().put("mail.smtp.auth", emailProperties.isSmtAuth());
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", emailProperties.isStartTLS());

        return mailSender;

    }
}
