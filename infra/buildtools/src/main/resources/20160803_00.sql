alter table ROLE drop constraint if exists role_name_unique_constraint;
drop index if exists role_name_unique_index;

alter table ROLE add constraint role_name_unique_constraint unique (NAME);
create index role_name_unique_index on ROLE (name);
alter table ROLE alter column NAME set not null;