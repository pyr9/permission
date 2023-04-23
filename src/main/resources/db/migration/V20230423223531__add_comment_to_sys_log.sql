comment on column sys_log.type is '权限更新的类型，1:部门，2:用户，3:权限模块，4:权限，5:角色，6:角色用户关系， 7:角色权限关系';

comment on column sys_log.target_id is '基于type后指定的对象Id, 比如用户，权限，角色表的主键';

comment on column sys_log.status is '当前是否复原过。0-复原过， 1: 没有';