DELETE FROM user_role;
DELETE FROM user_info;

INSERT INTO user_info (id, active, email, password, username)
VALUES
    (1, TRUE, 'some@mail.ru', '$2a$08$gRfrme/JwTMvcNlz/Hv9Re5gb7tlOYUDbyCWcKTF6GSKQIp75QcG.', 'dev'),
    (2, TRUE, 'some@mail.ru', '$2a$08$gRfrme/JwTMvcNlz/Hv9Re5gb7tlOYUDbyCWcKTF6GSKQIp75QcG.', 'user');

INSERT INTO user_role (user_id, roles)
VALUES
    (1, 'ADMIN'), (1, 'USER'),
    (2, 'USER');