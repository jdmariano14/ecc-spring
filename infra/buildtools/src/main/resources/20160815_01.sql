drop table if exists CONTACT;

create table CONTACT (
  CONTACT_ID bigint primary key,
  CONTACT_TYPE varchar(30),
  INFO varchar(30),
  PERSON_ID bigint references PERSON
);