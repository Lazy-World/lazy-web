DELETE FROM user_info;

INSERT INTO user_info (id, activation_code, active, email, password, username)
VALUES
    (1, 'testcode', FALSE, 'some@mail.ru', '$2a$08$gRfrme/JwTMvcNlz/Hv9Re5gb7tlOYUDbyCWcKTF6GSKQIp75QcG.', 'dev');