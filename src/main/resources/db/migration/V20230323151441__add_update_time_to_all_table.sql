alter table sys_acl
    add update_time timestamp;

alter table sys_acl_module
    add update_time timestamp;

alter table sys_department
    add update_time timestamp;

alter table sys_log
    add update_time timestamp;

alter table sys_role
    add update_time timestamp;

alter table sys_role_acl
    add update_time timestamp;

alter table sys_role_user
    add update_time timestamp;

alter table sys_user
    add update_time timestamp;

