alter table sys_user
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_acl
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_acl_module
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_department
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_log
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_role
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_role_acl
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_role_user
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_user
alter
column creator_id type bigint using creator_id::bigint;

alter table sys_role_acl
alter
column creator_id type bigint using creator_id::bigint;