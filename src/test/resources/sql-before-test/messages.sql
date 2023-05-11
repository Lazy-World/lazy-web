DELETE FROM message;
DELETE FROM chat_users;
DELETE FROM chat;

INSERT INTO chat (id)
VALUES
    (1);

INSERT INTO chat_users (chat_id, user_id)
VALUES
    (1, 1), (1, 2);

INSERT INTO message (id, message_date_time, text, user_id, chat_id)
VALUES
    (1, now(), 'cat from dev', 1, 1),
    (2, now(), 'cat from user', 2, 1);

ALTER SEQUENCE hibernate_sequence RESTART WITH 10;