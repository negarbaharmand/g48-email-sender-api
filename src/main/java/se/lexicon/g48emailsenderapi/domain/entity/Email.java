package se.lexicon.g48emailsenderapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Email {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name="_to", nullable = false)
    private String to;
    @Column(name="_from", nullable = false)
    private String from;
    private String subject;

    @Lob
    @Column (length =6500)
    private String content;

    @Lob
    private List<String> attachments;

    private LocalDateTime dateTime;

    @PrePersist
    public void initialDate(){
        dateTime = LocalDateTime.now();
    }
}
