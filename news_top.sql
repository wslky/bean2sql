/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云Mysql
Source Server Version : 50718
Source Host           : gz-cynosdbmysql-grp-mi1czca9.sql.tencentcdb.com:26365
Source Database       : qq_news

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2021-11-17 21:39:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for news_top
-- ----------------------------
DROP TABLE IF EXISTS `news_top`;
CREATE TABLE `news_top` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `img_url` varchar(200) DEFAULT NULL,
  `article_id` varchar(100) DEFAULT NULL,
  `comment_id` varchar(200) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4;
