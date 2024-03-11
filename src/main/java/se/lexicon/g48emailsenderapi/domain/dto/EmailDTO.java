package se.lexicon.g48emailsenderapi.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class EmailDTO {

    @NotBlank(message = "To field cannot blank.")
    @Email(message = "This field should be a valid email.")
    private String to;
    @NotBlank(message = "Subject field cannot blank.")
    private String subject;
    @NotBlank(message = "HTML field cannot blank.")
    private String html;
}
