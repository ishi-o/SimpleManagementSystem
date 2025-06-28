CREATE TABLE jhomework.client_record (
    crid BIGINT,
    cid BIGINT,
    visit_date DATE,
    CONSTRAINT pk_clientrecord PRIMARY KEY (crid),
    CONSTRAINT fk_cid_clientrec_to_clients FOREIGN KEY (cid) REFERENCES jhomework.clients(cid)
);