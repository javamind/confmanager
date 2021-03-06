CREATE TABLE T_USER
(
  login VARCHAR2(50) NOT NULL,
  password VARCHAR2(100),
  email VARCHAR2(100),
  first_name VARCHAR2(50),
  last_name VARCHAR2(50),
  activated NUMBER(1) default 0,
  lang_key VARCHAR2(5),
  activation_key VARCHAR2(20),
  active NUMBER(1),
    active_change_date DATE,
    change_user VARCHAR2(40),
    change_date DATE,
  CONSTRAINT user_pk PRIMARY KEY (login)
);

CREATE TABLE T_AUTHORITY
(
  name VARCHAR2(50) NOT NULL,
  CONSTRAINT authority_pk PRIMARY KEY (name)
);

CREATE TABLE T_USER_AUTHORITY
(
  login VARCHAR2(50) NOT NULL,
  name VARCHAR2(50) NOT NULL,
  CONSTRAINT user_authority_pk PRIMARY KEY (login, name),
  CONSTRAINT user_authority_fk1 FOREIGN KEY (name) REFERENCES T_AUTHORITY (name),
  CONSTRAINT user_authority_fk2 FOREIGN KEY (login) REFERENCES T_USER (login)
);
