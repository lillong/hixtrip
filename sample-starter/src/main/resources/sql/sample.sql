#todo
你的建表语句,包含索引
--  1.买家频繁查询我的订单, 高峰期并发100左右。实时性要求高:
--    一、mysql设计
--        订单库        1.采用分库处理，使用 buyer_id%n（买家id%n）n表示订单库的数量； 测试使用n=2
--                     2.采用分表处理,使用创建时间,可按月/周/天分表 ;测试使用月
--                     3.采用联合索引 买加id,创建时间,看查询条件如有需要可加卖家姓名
--        分表分库 读写分离 使用 shardingsphere-proxy 独立部署与业务层分离不使用shardingsphere-jdbc方案，部署配置放在
      --     ../doc/mysql/config-sharding.yaml
      --     ../doc/mysql/server.yaml
      --     ../doc/mysql/docker-compose.yaml
      -- 部署方案采用1主多从 目前配置了1主2从
--    二、redis缓存设计
--        设计一个SET列表 key使用xxx:buyer_id:,value使用订单id 排序使用create_time时间搓，设计缓存时长，创建订单时缓存，查询时先查缓存，缓存没有查数据库补偿
CREATE TABLE `t_order`
(
    `id`           bigint                                                       NOT NULL,
    `order_no`     bigint                                                       NOT NULL COMMENT '订单号',
    `price`        bigint                                                       NOT NULL COMMENT '价格',
    `amount`       bigint                                                       NOT NULL COMMENT '数量',
    `buyer_id`     bigint                                                       NOT NULL COMMENT '购买人数量',
    `buyer_name`   varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '买卖人名称',
    `sku_id`       bigint                                                       NOT NULL COMMENT '商品id',
    `seller_id`    bigint                                                       NOT NULL COMMENT '卖家id',
    `pay_status`   tinyint                                                      NOT NULL DEFAULT '0' COMMENT '支付状态,0=未支付,1=已支付,2=支付失败',
    `des`          varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         DEFAULT NULL COMMENT '描述',
    `del_flag`     int                                                                   DEFAULT NULL,
    `create_month` varchar(16)                                                           DEFAULT NULL COMMENT '月份',
    `create_by`    varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci          DEFAULT NULL COMMENT '创始人',
    `create_time`  datetime                                                              DEFAULT NULL,
    `update_by`    varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci          DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime                                                              DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY            `idx_order_no` (`order_no`),
    KEY            `idx_buyer` (`buyer_id`,`create_time`) USING BTREE,
    KEY            `idx_seller` (`seller_id`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
-- 2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
-- 设计一个SET列表 key使用xxx:seller_id:,value使用订单id 排序使用create_time时间搓，设计缓存时长。创建订单时缓存，查询时先查缓存，缓存没有查数据库补偿
-- 3.平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
-- 数据采集可以使用kafka,s数据库使用，Elasticsearch 。可以按order_index_20250101 月份进行分片处理。并做好6个月数据的冷热处理。
-- {
--   "order_id": "ORD202504210001",
--   "order_no": "123",
--   "buyer_name": "123",
--   "last_digits": "0001",          // 订单尾号
--   "status": "succeed",
--   "create_time": "2024-12-12T10:15:30",
-- }
-- 4.平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
--  使用其他组件 Kafka  flink  redis  clickHouse ,使用clickHouse等大数据类型的数据是为了将来做复杂的数据分析
--  数据来源 kafka
--  Flink 实时处理并计算买家和卖家的订单总额，这里可以使用开窗比如1分钟开一次，看数据的延迟性。
--  Redis  存储实时的排行榜数据（有序集合 ZSET）
--  ClickHouse：存储历史订单数据，用于周期性分析和报表生成。






