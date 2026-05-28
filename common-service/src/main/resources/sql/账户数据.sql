create database if not exists abdems;
use abdems;
drop table if exists account;
create table if not exists account(
      id int primary key auto_increment comment '主键',
      name varchar(100) not null unique comment '账户名称',
      money double(10,2) comment '账户余额',
      home_address varchar(200) comment '家庭地址'

);
insert into account (name, money,home_address)
values
    ('tom',10000,'北京昌平区'),
    ('jack',50000,'浙江杭州余杭区'),
    ('lucy',80000,'江苏徐州沛县'),
    ('jerry',30000,'山东聊城高唐县'),
    ('kitty',60000,'浙江杭州西湖区'),
    ('merry',20000,'湖北武汉江汉区'),
    ('lily',70000,'湖南长沙芙蓉区');