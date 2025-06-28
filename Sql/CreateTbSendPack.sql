CREATE TABLE jhomework.send_packages (
    crid BIGINT,
    fee INT,
    send_date DATE,
    CONSTRAINT pk_sendpack PRIMARY KEY (crid),
    CONSTRAINT fk_crid_sendpack_to_clirec FOREIGN KEY (crid) REFERENCES jhomework.client_record(crid)
);