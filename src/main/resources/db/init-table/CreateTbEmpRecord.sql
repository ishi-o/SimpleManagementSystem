DROP TABLE IF EXISTS jhomework.emp_records;

CREATE TABLE jhomework.emp_records (
    eid BIGINT,
    check_date DATE,
    CONSTRAINT pk_emprecords PRIMARY KEY (eid, check_date),
    CONSTRAINT fk_eid_ref_emps FOREIGN KEY (eid) REFERENCES jhomework.employees(eid)
);

SELECT * FROM jhomework.emp_records;