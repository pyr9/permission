alter table sys_acl
    rename column creator to creator_id;

alter table sys_acl
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_acl_module
    rename column creator to creator_id;

alter table sys_acl_module
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_department
    rename column creator to creator_id;

alter table sys_department
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_log
    rename column creator to creator_id;

alter table sys_log
alter
column creator_id type INTEGER using creator_id::INTEGER;



alter table sys_role
    rename column creator to creator_id;

alter table sys_role
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_role_acl
    rename column creator to creator_id;

alter table sys_role_acl
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_role_user
    rename column creator to creator_id;

alter table sys_role_user
alter
column creator_id type INTEGER using creator_id::INTEGER;

alter table sys_user
    rename column creator to creator_id;

alter table sys_user
alter
column creator_id type INTEGER using creator_id::INTEGER;