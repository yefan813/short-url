/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost
 Source Database       : short_url

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 03/24/2020 17:35:46 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_short_url`
-- ----------------------------
DROP TABLE IF EXISTS `tb_short_url`;
CREATE TABLE `tb_short_url` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `hash_value` varchar(32) NOT NULL,
  `url` varchar(2048) NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `yn` tinyint(2) DEFAULT '0' COMMENT '是否删除 1 删除 0 未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`hash_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=325781 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
