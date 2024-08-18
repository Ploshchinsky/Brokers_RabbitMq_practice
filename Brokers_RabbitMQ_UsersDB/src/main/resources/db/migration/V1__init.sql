CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE user_subs
(
    user_id      BIGINT       NOT NULL,
    sub_username VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO users (username)
VALUES ('john_doe'),
       ('jane_smith'),
       ('alice_wonder'),
       ('bob_marley'),
       ('charlie_brown'),
       ('david_jones'),
       ('eva_green'),
       ('frank_white'),
       ('george_hill'),
       ('hannah_black');

INSERT INTO user_subs (user_id, sub_username)
VALUES ((SELECT id FROM users WHERE username = 'john_doe'), 'jane_smith'),
       ((SELECT id FROM users WHERE username = 'john_doe'), 'alice_wonder'),

       ((SELECT id FROM users WHERE username = 'jane_smith'), 'john_doe'),
       ((SELECT id FROM users WHERE username = 'jane_smith'), 'bob_marley'),

       ((SELECT id FROM users WHERE username = 'alice_wonder'), 'charlie_brown'),
       ((SELECT id FROM users WHERE username = 'alice_wonder'), 'david_jones'),

       ((SELECT id FROM users WHERE username = 'bob_marley'), 'eva_green'),
       ((SELECT id FROM users WHERE username = 'bob_marley'), 'frank_white'),

       ((SELECT id FROM users WHERE username = 'charlie_brown'), 'george_hill'),
       ((SELECT id FROM users WHERE username = 'charlie_brown'), 'hannah_black'),

       ((SELECT id FROM users WHERE username = 'david_jones'), 'john_doe'),
       ((SELECT id FROM users WHERE username = 'david_jones'), 'jane_smith'),

       ((SELECT id FROM users WHERE username = 'eva_green'), 'alice_wonder'),
       ((SELECT id FROM users WHERE username = 'eva_green'), 'bob_marley'),

       ((SELECT id FROM users WHERE username = 'frank_white'), 'charlie_brown'),
       ((SELECT id FROM users WHERE username = 'frank_white'), 'david_jones'),

       ((SELECT id FROM users WHERE username = 'george_hill'), 'eva_green'),
       ((SELECT id FROM users WHERE username = 'george_hill'), 'frank_white'),

       ((SELECT id FROM users WHERE username = 'hannah_black'), 'george_hill'),
       ((SELECT id FROM users WHERE username = 'hannah_black'), 'john_doe');
