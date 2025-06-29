DROP TABLE IF EXISTS jhomework.client_records;

CREATE TABLE jhomework.client_records (
    crid BIGINT,
    cid BIGINT,
    visit_date TIMESTAMP,
    CONSTRAINT pk_clientrecords PRIMARY KEY (crid),
    CONSTRAINT fk_cid_ref_clients FOREIGN KEY (cid) REFERENCES jhomework.clients(cid)
);

SELECT * FROM jhomework.client_records;

DELETE FROM jhomework.client_records WHERE 1=1;
TRUNCATE TABLE jhomework.client_records;