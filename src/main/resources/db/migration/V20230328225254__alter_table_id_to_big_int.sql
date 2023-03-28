alter table sys_user
alter
column id type bigint using id::bigint;

alter table sys_acl
alter
column id type bigint using id::bigint;

alter table sys_acl_module
alter
column id type bigint using id::bigint;

alter table sys_department
alter
column id type bigint using id::bigint;

alter table sys_log
alter
column id type bigint using id::bigint;

alter table sys_role
alter
column id type bigint using id::bigint;

alter table sys_role_acl
alter
column id type bigint using id::bigint;

alter table sys_role_user
alter
column id type bigint using id::bigint;

alter table sys_user
alter
column id type bigint using id::bigint;

alter table sys_role_acl
alter
column id type bigint using id::bigint;