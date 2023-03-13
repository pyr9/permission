create table sys_acl_module
(
    id          int          not null
        primary key,
    name        varchar(255) not null,
    parent_id   int          not null,
    level       varchar(255) not null,
    status      int          not null,
    seq         int          not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null
);

create table sys_department
(
    id          int          not null
        primary key,
    name        varchar(255) not null,
    parent_id   int          not null,
    level       varchar(255) not null,
    seq         int          not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null
);

create table sys_log
(
    id          int          not null
        primary key,
    type        int          not null,
    target_id   int          not null,
    old_value   varchar(255) not null,
    new_value   varchar(255) not null,
    status      int          not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null
);

create table sys_role
(
    id          int          not null
        primary key,
    name        varchar(255) not null,
    type        int          not null,
    status      int          not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null
);

create table sys_user
(
    id            int          not null
        primary key,
    user_name     varchar(255) not null,
    telephone     varchar(255) not null,
    mail          varchar(255) not null,
    passward      varchar(255) not null,
    department_id int          not null,
    status        int          not null,
    remark        varchar(255) null,
    creator       varchar(255) null,
    create_time   timestamp null,
    creatorIp     varchar(255) null,
    constraint sys_user_department_id_foreign
        foreign key (department_id) references sys_department (id)
);

create table sys_acl
(
    id            int          not null
        primary key,
    code          varchar(255) not null,
    name          varchar(255) not null,
    acl_module_Id int          not null,
    url           varchar(255) not null,
    type          int          not null,
    status        int          not null,
    seq           int          not null,
    remark        varchar(255) null,
    creator       varchar(255) null,
    create_time   timestamp null,
    creatorIp     varchar(255) null,
    constraint sys_acl_acl_module_id_foreign
        foreign key (acl_module_Id) references sys_acl_module (id)
);

create table sys_role_acl
(
    id          int not null
        primary key,
    role_id     int not null,
    acl_id      int not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null,
    constraint sys_role_acl_acl_id_foreign
        foreign key (acl_id) references sys_acl (id),
    constraint sys_role_acl_role_id_foreign
        foreign key (role_id) references sys_role (id)
);

create table sys_role_user
(
    id          int not null
        primary key,
    user_id     int not null,
    role_id     int not null,
    remark      varchar(255) null,
    creator     varchar(255) null,
    create_time timestamp null,
    creatorIp   varchar(255) null,
    constraint sys_role_user_role_id_foreign
        foreign key (role_id) references sys_role (id),
    constraint sys_role_user_user_id_foreign
        foreign key (user_id) references sys_user (id)
);

