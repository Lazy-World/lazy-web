DELETE FROM update_log;

INSERT INTO update_log(id, filename, text, title, user_id)
VALUES
    (1, 'NONE', 'FIRST TEXT', 'CAT1', 1),
    (2, 'NONE', 'SECOND TEXT', 'CAT2', 1),
    (3, 'NONE', 'THIRD TEXT', 'CAT1', 1),
    (4, 'NONE', 'FOURTH TEXT', 'CAT4', 1);

ALTER SEQUENCE hibernate_sequence RESTART WITH 10;