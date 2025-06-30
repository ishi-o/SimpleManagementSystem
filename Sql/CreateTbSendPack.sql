DROP TABLE IF EXISTS jhomework.send_packages;
CREATE TABLE jhomework.send_packages (
    pid BIGINT,
    crid BIGINT,
    fee INT,
    proc_date TIMESTAMP,
    CONSTRAINT pk_sendpack PRIMARY KEY (pid),
    CONSTRAINT fk_crid_ref_clientrecords FOREIGN KEY (crid) REFERENCES jhomework.client_records(crid)
);

SELECT * FROM jhomework.send_packages;
