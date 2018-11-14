/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : ymq_one

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2017-10-20 12:51:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_one
-- ----------------------------
DROP TABLE IF EXISTS `test_one`;
CREATE TABLE `test_one` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='测试';

-- ----------------------------
-- Records of test_one
-- ----------------------------
INSERT INTO `test_one` VALUES ('1', '测试', '这是测试 ymq_one 数据库');
