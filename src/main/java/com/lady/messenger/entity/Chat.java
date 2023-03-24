package com.lady.messenger.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messageList;

    public Chat() {
        List<Message> emptyMessageList = Collections.emptyList();
        this.messageList = new ArrayList<>(emptyMessageList);
        users = new ArrayList<>();
    }

    public Chat(List<User> users) {
        List<Message> emptyMessageList = Collections.emptyList();
        this.messageList = new ArrayList<>(emptyMessageList);

        this.users = users;
    }
}
