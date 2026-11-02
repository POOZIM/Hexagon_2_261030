-- 급여 대장
CREATE TABLE salary (
    salary_id          NUMBER        NOT NULL,
    emp_id             NUMBER        NOT NULL,
    salary_year_month  VARCHAR2(7)   NOT NULL,
    base_salary        NUMBER(15, 0) NOT NULL,
    bonus              NUMBER(15, 0) DEFAULT 0 NOT NULL,
    deduction          NUMBER(15, 0) DEFAULT 0 NOT NULL,
    net_salary         NUMBER(15, 0) NOT NULL,
    payment_date       DATE,

    CONSTRAINT pk_salary
        PRIMARY KEY (salary_id),

    CONSTRAINT fk_salary_emp
        FOREIGN KEY (emp_id)
        REFERENCES emp(emp_id)
);

-- 급여 번호 발급
CREATE SEQUENCE seq_salary
    START WITH 1
    INCREMENT BY 1;