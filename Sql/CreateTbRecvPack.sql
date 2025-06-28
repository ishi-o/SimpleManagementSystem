DROP TABLE IF EXISTS jhomework.recv_packages;

CREATE TABLE jhomework.recv_packages (
    pid BIGINT,
    recv_eid BIGINT,
    proc_date DATE,
    CONSTRAINT pk_recvpack PRIMARY KEY (pid),
    CONSTRAINT fk_recveid_to_emps FOREIGN KEY (recv_eid) REFERENCES jhomework.employees(eid)
);