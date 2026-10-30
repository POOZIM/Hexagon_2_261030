-- 역할
create table hr_role(
	hr_role_id number primary key 
	, role_code varchar2(30)
	, role_name varchar2(30)
	, is_active number(1) default 1
);

create sequence seq_hr_role
increment by 1
start with 1;


-- 권한 
create table permission(
	permission_id number primary key
	, permission_code varchar2(40)
	, permission_name varchar2(30)
);

create sequence seq_permission
increment by 1
start with 1;

-- 역할별 권한 
create table hr_role_permission(
	hr_role_permission_id number primary key 
	, hr_role_id number
	, permission_id number
	, constraint fk_hr_role_permission_hr_role foreign key(hr_role_id) references hr_role(hr_role_id)
	, constraint fk_hr_role_permission_permission foreign key (permission_id) references permission(permission_id)
);

create sequence seq_hr_role_permission
increment by 1
start with 1;


-- 부서  
create table department(
	department_id number primary key 
	, department_code varchar2(20)
	, department_name	varchar2(20)
	, is_active number(1) default 1
);

create sequence seq_department
increment by 1
start with 1;

-- 직급  
create table job(
	job_id number primary key
	, job_code varchar2(30) 
	, job_name varchar2(15)
	, is_active number(1) default 1
);

create sequence seq_job
increment by 1
start with 1;


-- 직책 
create table hr_position (
    hr_position_id number primary key
    , position_code varchar2(50) not null
    , position_name varchar2(100) not null
    , is_active number(1) default 1
);

create sequence seq_hr_position
increment by 1
start with 1;


-- 사원  
create table emp(
	emp_id number primary key 
	, ename varchar2(30)
	, email varchar2(40)
	, phone varchar2(13)
	, hiredate date 	
	, department_id number --소속 부서
	, job_id number -- 직급
	, hr_position_id number -- 직책
	, status varchar2(15) default 'ACTIVE'
	, annual_leave_days number -- 휴가일
	, constraint fk_emp_department foreign key(department_id) references department(department_id)
	, constraint fk_emp_job foreign key(job_id) references job(job_id)
	, constraint fk_emp_hr_position foreign key(hr_position_id) references hr_position(hr_position_id)
);

create sequence seq_emp
increment by 1
start with 1;

-- 사원 이용 시스템 정보  
create table account(
	account_id number primary key 
	, emp_id number  
	, login_id varchar2(25)
	, password varchar2(255)
	, enabled number(1) default 1
	, created_at date default sysdate
	, last_login_at date
	, constraint fk_account_emp foreign key(emp_id) references emp(emp_id)
);

create sequence seq_account
increment by 1
start with 1;


-- 각 account  에 부여된 Role
create table account_role(
	account_role_id number primary key 
	, account_id number
	, hr_role_id number 
	, constraint fk_account_role_account foreign key(account_id) references account(account_id)
	, constraint fk_account_role_hr_role foreign key(hr_role_id) references hr_role(hr_role_id)
);

create sequence seq_account_role
increment by 1
start with 1;


-- 휴가 
create table leave_request(
	leave_request_id number primary key 
	, emp_id number 
	, start_date date 
	, end_date date
	, days number 
	, reason varchar2(50)
	, status varchar2(15)
	, request_date date 
	, approver_id number -- 승인자
	, approved_at date -- 승인일
	, constraint fk_leave_request_emp foreign key(emp_id) references emp(emp_id)
	, constraint fk_leave_request_approver foreign key(approver_id) references emp(emp_id) 
);

create sequence seq_leave_request
increment by 1
start with 1;