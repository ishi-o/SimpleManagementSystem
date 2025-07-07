DROP TABLE IF EXISTS jhomework.clients;

CREATE TABLE jhomework.clients (
    cid BIGINT,
    cname VARCHAR(255) NOT NULL,
    phonenum VARCHAR(255) NOT NULL,
    loc VARCHAR(255) NOT NULL,
    CONSTRAINT pk_clients PRIMARY KEY (cid),
    CONSTRAINT unq_phone_clients UNIQUE (phonenum),
    CONSTRAINT ck_phonenum_clients CHECK (
        phonenum ~ '^[0-9]{11}$'
    )
);

SELECT * FROM jhomework.clients;

DELETE FROM jhomework.clients WHERE 1=1;
DELETE FROM jhomework.client_records WHERE 1=1;
DELETE FROM jhomework.emp_records WHERE 1=1;
DELETE FROM jhomework.employees WHERE 1=1;
DELETE FROM jhomework.recv_packages WHERE 1=1;
DELETE FROM jhomework.send_packages WHERE 1=1;
DELETE FROM jhomework.users WHERE 1=1;