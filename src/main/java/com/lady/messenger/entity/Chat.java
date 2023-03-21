package com.lady.messenger.entity;

import javax.persistence.*;
import java.util.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
