package com.lady.messenger.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "update_log")
public class UpdateLog {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the text")
    @Length(max = 2048, message = "Your update log is too long...")
    private String text;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename = "<none>";

    public UpdateLog(Long id, String title, String text, User user)
    {
        this.id = id;
        this.title = title;
        this.text = text;

        this.author = user;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateLog updateLog = (UpdateLog) o;
        return id.equals(updateLog.id) && text.equals(updateLog.text) && title.equals(updateLog.title) && author.equals(updateLog.author) && Objects.equals(filename, updateLog.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, title, author, filename);
    }
}
