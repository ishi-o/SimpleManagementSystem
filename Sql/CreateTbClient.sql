CREATE TABLE jhomework.clients (
    cid BIGINT,
    cname VARCHAR(255) NOT NULL,
    phonenum VARCHAR(255) NOT NULL,
    loc VARCHAR(255) NOT NULL,
    CONSTRAINT pk_clients PRIMARY KEY (cid),
    CONSTRAINT unq_phone_clients UNIQUE (phonenum)
);

SELECT * FROM jhomework.clients;