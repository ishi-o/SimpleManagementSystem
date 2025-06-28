DROP TABLE IF EXISTS jhomework.send_packages;
CREATE TABLE jhomework.send_packages (
    pid BIGINT,
    crid BIGINT,
    fee INT,
    proc_date DATE,
    CONSTRAINT pk_sendpack PRIMARY KEY (pid),
    CONSTRAINT fk_crid_sendpack_to_clirec FOREIGN KEY (crid) REFERENCES jhomework.client_record(crid)
);

SELECT * FROM jhomework.send_packages;
