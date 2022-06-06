/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 8.0.27
Source Host           : 127.0.0.1:3306
Source Database       : entertainment

Target Server Type    : MYSQL
Target Server Version : 8.0.27
File Encoding         : 65001

Date: 2022-06-06 14:32:39
*/

-- ----------------------------
-- Table structure for book
-- 书籍表
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`                 int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `book_cn_name`       varchar(128)   DEFAULT NULL COMMENT '书的中文名',
    `book_origin_name`   varchar(128)   DEFAULT NULL COMMENT '书的原名',
    `author_cn_name`     varchar(128)   DEFAULT NULL COMMENT '作者的中文名',
    `author_origin_name` varchar(128)   DEFAULT NULL COMMENT '作者的原名',
    `country`            varchar(16)    DEFAULT NULL COMMENT '原版书籍（作者）国家',
    `book_language`           varchar(16)    DEFAULT NULL COMMENT '原版语言',
    `category`           varchar(16)    DEFAULT NULL COMMENT '类别（一级）',
    `book_type`               varchar(16)    DEFAULT NULL COMMENT '类型（二级）',
    `publishing_house`   varchar(64)    DEFAULT NULL COMMENT '出版社',
    `isbn`               int(13) DEFAULT NULL COMMENT 'ISBN',
    `publishing_time`    date       DEFAULT NULL COMMENT '出版时间',
    `page_size`    int(8) DEFAULT NULL COMMENT '页数',
    `text_size`          int(11) DEFAULT NULL COMMENT '字数',
    `price`              decimal(10, 2) DEFAULT NULL COMMENT '售价',
    `purchase_price`              decimal(10, 2) DEFAULT NULL COMMENT '实际购买价格',
    `is_purchase`              tinyint(1) unsigned DEFAULT NULL COMMENT '是否已经购买',
    `is_read`               tinyint(1) unsigned DEFAULT NULL COMMENT '是否已经看完',
    `create_time`        datetime       DEFAULT NULL COMMENT '创建时间',
    `last_update_time`   datetime       DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY                  `idx_book_cn_name` (`book_cn_name`),
    KEY                  `idx_author_cn_name` (`author_cn_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书籍表';

