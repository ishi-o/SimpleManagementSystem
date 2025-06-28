DROP TABLE IF EXISTS jhomework.emp_record;

CREATE TABLE jhomework.emp_record (
    eid BIGINT,
    check_date DATE,
    CONSTRAINT pk_emprecord PRIMARY KEY (eid, check_date),
    CONSTRAINT fk_eid_emprecord_to_emps FOREIGN KEY (eid) REFERENCES jhomework.employees(eid)
);

SELECT * FROM jhomework.emp_record;