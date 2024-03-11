package se.lexicon.g48emailsenderapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g48emailsenderapi.domain.dto.EmailDTO;

@Service
public interface EmailService {
    Void sendEmail(EmailDTO emailDTO);
}
