/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云Mysql
Source Server Version : 50718
Source Host           : gz-cynosdbmysql-grp-mi1czca9.sql.tencentcdb.com:26365
Source Database       : qq_news

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2021-11-17 21:40:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for real_news
-- ----------------------------
DROP TABLE IF EXISTS `real_news`;
CREATE TABLE `real_news` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `cms_id` varchar(30) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `thumb_nail` varchar(255) DEFAULT NULL,
  `thumb_nail_2x` varchar(255) DEFAULT NULL,
  `media_name` varchar(255) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=571 DEFAULT CHARSET=utf8mb4;
