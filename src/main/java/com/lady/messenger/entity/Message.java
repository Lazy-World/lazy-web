package com.lady.messenger.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Fill the text")
    @Length(max=1024, message = "Your message is too long...")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @JoinColumn(name = "message_datetime")
    private LocalDateTime messageDateTime;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public String getMessageDateString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return messageDateTime.format(myFormatObj);
    }

    public String getMessageTimeString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
        return messageDateTime.format(myFormatObj);
    }
}
