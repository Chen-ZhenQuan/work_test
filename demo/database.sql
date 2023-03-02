create database user_management_system;
use user_management_system;
create table user(
                     id int auto_increment primary key,
                     name varchar(25) not null comment '用户姓名',
                     gender tinyint(1) not null comment '性别：1为男，0为女',
                     age int not null comment '年龄',
                     phone bigint not null comment '联系电话',
                     address char(20) comment '省/市/区',
                     detailed_address varchar(100) not null comment '信息地址'
) COMMENT '用户表';
