DROP table IF EXISTS `t_order`;

create table `t_order`
(
    id           bigint                                NOT NULL,
    place_time   timestamp   default current_timestamp not null comment '下单时间',
    user_id      bigint                                not null comment '用户ID',
    `status`     smallint(1)                           not null comment '订单状态 0-有效 1-取消',
    `pay_status` smallint(1)                           not null comment '支付状态 0-未支付 1-已支付',
    price        int                                   not null comment '订单总价格*1000',
    create_time  timestamp   default current_timestamp not null comment '创建时间',
    create_by    varchar(20)                           not null comment '创建人',
    update_time  timestamp   default current_timestamp not null comment '修改时间',
    update_by    varchar(20)                           not null comment '修改人',
    is_deleted   smallint(1) default 0                 not null comment '是否被删除 0-否 1-是',
    constraint order_pk
        primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

create index idx_t_order_1
    on `t_order` (user_id);