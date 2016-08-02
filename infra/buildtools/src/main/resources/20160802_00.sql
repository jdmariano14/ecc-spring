drop table if exists PERSON;
drop table if exists ROLE;
drop table if exists CONTACT_DETAIL;
drop table if exists PERSON_ROLE;

create table PERSON (
  PERSON_ID bigint primary key,
  BIRTH_DATE date,
  GWA numeric(6,5),
  DATE_HIRED date,
  EMPLOYED boolean,
  -- NAME FIELDS
  FIRST_NAME varchar(30),
  LAST_NAME varchar(30),
  MIDDLE_NAME varchar(30),
  SUFFIX varchar(5),
  TITLE varchar(10),
  -- ADDRESS FIELDS
  STREET_ADDRESS varchar(30),
  BARANGAY varchar(30),
  MUNICIPALITY varchar(30),
  ZIP_CODE varchar(10)
);

create table ROLE (
  ROLE_ID bigint primary key,
  NAME varchar(30)
);

create table CONTACT_DETAIL (
  CONTACT_DETAIL_ID bigint primary key,
  CONTACT_DETAIL_TYPE varchar(30),
  DETAIL varchar(30),
  PERSON_ID bigint references PERSON
);

create table PERSON_ROLE (
  PERSON_ID bigint references PERSON,
  ROLE_ID bigint references ROLE,
  primary key (PERSON_ID, ROLE_ID)
);