
DROP table IF EXISTS `order`;

create table `order`
(
	id bigint auto_increment,
	place_time timestamp default current_timestamp not null comment '下单时间',
	user_id bigint not null comment '用户ID',
	`status` smallint(1) not null comment '订单状态 0-有效 1-取消',
	`pay_status` smallint(1) not null comment '支付状态 0-未支付 1-已支付',
	price int not null comment '订单总价格*1000',
	create_time timestamp default current_timestamp not null comment '创建时间',
	create_by varchar(20) not null comment '创建人',
	update_time timestamp default current_timestamp not null comment '修改时间',
	update_by varchar(20) not null comment '修改人',
	is_deleted smallint(1) default 0 not null comment '是否被删除 0-否 1-是',
	constraint order_pk
		primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

create index order__index_1
	on `order` (user_id);
	
DROP table IF EXISTS `order_detail`;
	
create table order_detail
(
	id bigint auto_increment,
	order_id bigint not null comment '订单ID',
	product_id bigint not null comment '商品ID',
	price int not null comment '商品总交易价格*1000',
	create_time timestamp default current_timestamp not null comment '创建时间',
	create_by varchar(20) not null comment '创建人',
	update_time timestamp default current_timestamp not null comment '修改时间',
	update_by varchar(20) not null comment '修改人',
	is_deleted smallint(1) default 0 not null comment '是否被删除 0-否 1-是',
	quality int not null comment '购买数量',
	constraint order_detail_pk
		primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '订单详情';

create index order_detail__index_1
	on order_detail (order_id);	

DROP table IF EXISTS `user`;
	
create table `user`
(
	id bigint auto_increment,
	name varchar(30) not null comment '姓名',
	register_time timestamp default current_timestamp not null comment '注册时间',
	status smallint(1) default 0 not null comment '用户状态 0-有效 1-冻结',
	create_time timestamp default current_timestamp not null comment '创建时间',
	create_by varchar(20) not null comment '创建人',
	update_time timestamp default current_timestamp not null comment '修改时间',
	update_by varchar(20) not null comment '修改人',
	is_deleted smallint(1) default 0 not null comment '是否被删除 0-否 1-是',
	account varchar(30) not null comment '用户账号',
	constraint user_pk
		primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '用户表';


DROP table IF EXISTS `product`;
create table product
(
	id bigint auto_increment,
	name varchar(30) not null comment '名称',
	register_time timestamp default current_timestamp not null comment '注册时间',
	status smallint(1) default 0 not null comment '状态 0-上架 1-下架',
	create_time timestamp default current_timestamp not null comment '创建时间',
	create_by varchar(20) not null comment '创建人',
	update_time timestamp default current_timestamp not null comment '修改时间',
	update_by varchar(20) not null comment '修改人',
	is_deleted smallint(1) default 0 not null comment '是否被删除 0-否 1-是',
	available_quality int not null comment '可用库存量',
	constraint product_pk
		primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '商品表';






	
