alter table sys_acl
alter
column acl_module_id type bigint using acl_module_id::bigint;

alter table sys_acl_module
alter
column parent_id type bigint using parent_id::bigint;

alter table sys_department
alter
column parent_id type bigint using parent_id::bigint;

alter table sys_log
alter
column target_id type bigint using target_id::bigint;

alter table sys_role_acl
alter
column acl_id type bigint using acl_id::bigint;

alter table sys_role_acl
alter
column role_id type bigint using role_id::bigint;

alter table sys_role_user
alter
column role_id type bigint using role_id::bigint;

alter table sys_role_user
alter
column user_id type bigint using user_id::bigint;

alter table sys_user
alter
column department_id type bigint using department_id::bigint;