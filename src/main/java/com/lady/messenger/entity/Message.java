package com.lady.messenger.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Message() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getMessageDateTime() {
        return messageDateTime;
    }

    public String getMessageDateTimeString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        return messageDateTime.format(myFormatObj);
    }

    public void setMessageDateTime(LocalDateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
