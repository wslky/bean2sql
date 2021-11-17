/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云Mysql
Source Server Version : 50718
Source Host           : gz-cynosdbmysql-grp-mi1czca9.sql.tencentcdb.com:26365
Source Database       : qq_news

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2021-11-17 21:40:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for today_topic
-- ----------------------------
DROP TABLE IF EXISTS `today_topic`;
CREATE TABLE `today_topic` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `article_id` varchar(50) DEFAULT NULL,
  `comment_id` varchar(50) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4;
