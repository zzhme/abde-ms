-- =========================================
-- 产品表（在已有业务库中执行，例如 jhytest 或 abde_db）
-- =========================================
drop table if exists product;

create table product (
    id bigint unsigned primary key auto_increment comment '产品ID',
    name varchar(100) not null comment '产品名称',
    description varchar(500) default null comment '产品简介',
    cover_url varchar(500) default null comment '封面图URL（OSS等）',
    price decimal(12, 2) not null default 0.00 comment '单价',
    status tinyint unsigned not null default 1 comment '状态: 1上架 0下架',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) engine=innodb default charset=utf8mb4 comment='产品表';

create index idx_product_name on product(name);
create index idx_product_status on product(status);

-- =========================================
-- 测试数据（cover_url 可替换为你实际上传后的 OSS 地址）
-- =========================================
insert into product (name, description, cover_url, price, status)
values
    ('Spring Boot 实战教程', '从入门到项目实战，含 JWT、MyBatis、OSS 上传等', 'https://example.com/covers/spring-boot.png', 99.00, 1),
    ('Vue2 管理后台模板', 'Element UI 表格、表单、路由守卫示例', 'https://example.com/covers/vue-admin.png', 49.90, 1),
    ('MySQL 性能优化笔记', '索引、执行计划、分页与慢查询', 'https://example.com/covers/mysql.png', 68.00, 0),
    ('阿里云 OSS 对象存储入门', 'Java SDK 上传与访问域名说明', 'https://example.com/covers/oss.png', 19.90, 1);
