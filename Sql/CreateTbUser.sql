DROP TABLE IF EXISTS jhomework.users;
CREATE TABLE jhomework.users (
    uid BIGINT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (uid),
    CONSTRAINT uniq_username_users UNIQUE (username),
    CONSTRAINT ck_username_users CHECK (
        username ~ '^(?=.*[a-zA-Z0-9]).+$' AND
        length(username) <= 20
    ),
    CONSTRAINT ck_password_users CHECK (
        password ~ '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&+=]).+$' AND
        length(password) BETWEEN 8 AND 20
    )
);
SELECT * FROM jhomework.users;

-- INSERT INTO jhomework.users VALUES (
--     1, 'testuser', 'TruePwd@1234'
-- );

-- DELETE FROM jhomework.users WHERE uid = 1;