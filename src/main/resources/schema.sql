-- Sequences
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE merchant_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE establishment_seq START WITH 1 INCREMENT BY 1;

-- Tables
CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    role VARCHAR2(20) CHECK (role IN ('ADMIN', 'REGISTRATION_ASSISTANT')) NOT NULL
);

CREATE TABLE merchants (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    city VARCHAR2(100) NOT NULL,
    phone VARCHAR2(20),
    email VARCHAR2(100),
    registration_date DATE NOT NULL,
    status VARCHAR2(10) CHECK (status IN ('ACTIVE', 'INACTIVE')) NOT NULL,
    created_by VARCHAR2(100) NOT NULL,
    updated_by VARCHAR2(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);

CREATE TABLE establishments (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    income NUMBER(15,2) NOT NULL,
    employee_count NUMBER NOT NULL,
    merchant_id NUMBER NOT NULL,
    created_by VARCHAR2(100) NOT NULL,
    updated_by VARCHAR2(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_merchant FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

-- Triggers for ID generation
CREATE OR REPLACE TRIGGER user_id_trigger
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SELECT user_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER merchant_id_trigger
BEFORE INSERT ON merchants
FOR EACH ROW
BEGIN
    SELECT merchant_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER establishment_id_trigger
BEFORE INSERT ON establishments
FOR EACH ROW
BEGIN
    SELECT establishment_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Triggers for audit fields
CREATE OR REPLACE TRIGGER merchant_audit_trigger
BEFORE INSERT OR UPDATE ON merchants
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        :NEW.creation_date := SYSTIMESTAMP;
        :NEW.created_by := USER;
    END IF;
    :NEW.update_date := SYSTIMESTAMP;
    :NEW.updated_by := USER;
END;
/

CREATE OR REPLACE TRIGGER establishment_audit_trigger
BEFORE INSERT OR UPDATE ON establishments
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        :NEW.creation_date := SYSTIMESTAMP;
        :NEW.created_by := USER;
    END IF;
    :NEW.update_date := SYSTIMESTAMP;
    :NEW.updated_by := USER;
END;
/

-- Function for merchant report
CREATE OR REPLACE FUNCTION get_active_merchants_report
RETURN SYS_REFCURSOR
IS
    result_cursor SYS_REFCURSOR;
BEGIN
    OPEN result_cursor FOR
        SELECT
            m.name AS merchant_name,
            m.municipality,
            m.phone,
            m.email,
            m.registration_date,
            m.status,
            COUNT(e.id) AS establishment_count,
            NVL(SUM(e.income), 0) AS total_income,
            NVL(SUM(e.employee_count), 0) AS total_employees
        FROM
            merchants m
        LEFT JOIN
            establishments e ON m.id = e.merchant_id
        WHERE
            m.status = 'ACTIVE'
        GROUP BY
            m.name, m.municipality, m.phone, m.email, m.registration_date, m.status
        ORDER BY
            establishment_count DESC;

    RETURN result_cursor;
END;
/