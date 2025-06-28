CREATE TABLE jhomework.emp_record (
    eid BIGINT,
    check_date DATE,
    CONSTRAINT pk_emprecord PRIMARY KEY (eid),
    CONSTRAINT fk_eid_emprecord_to_emps FOREIGN KEY (eid) REFERENCES jhomework.employees(eid)
);

SELECT * FROM jhomework.emp_record;