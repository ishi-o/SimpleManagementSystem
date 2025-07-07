DROP TABLE IF EXISTS jhomework.employees;

CREATE TABLE jhomework.employees (
    eid BIGINT, 
    ename VARCHAR(255),
    joindate DATE,
    CONSTRAINT pk_employees PRIMARY KEY (eid)
);

SELECT * FROM jhomework.employees;