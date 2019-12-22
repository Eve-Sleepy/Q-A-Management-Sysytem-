/*
 Navicat Premium Data Transfer

 Source Server         : FAQ
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : mydb

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 13/12/2019 08:12:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for docs
-- ----------------------------
DROP TABLE IF EXISTS `docs`;
CREATE TABLE `docs`  (
  `id` int(10) NOT NULL COMMENT '文档id',
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档标题',
  `content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正文',
  `author_id` int(10) NOT NULL COMMENT '所属用户id',
  `edition` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本',
  `operation` int(1) NULL DEFAULT NULL COMMENT '文档操作，0为上传，1为更新',
  `operation_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最近操作时间',
  `created_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `product_id` int(10) NOT NULL COMMENT '产品id',
  `dept_belong` int(1) NULL DEFAULT NULL COMMENT '所属部门 0为设计，1为开发，2为实施，3为测试',
  `is_use` int(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_doc_product1_idx`(`product_id`) USING BTREE,
  INDEX `fk_docs_users1_idx`(`author_id`) USING BTREE,
  CONSTRAINT `fk_doc_product1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_docs_users1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msgs
-- ----------------------------
DROP TABLE IF EXISTS `msgs`;
CREATE TABLE `msgs`  (
  `id` int(100) NOT NULL COMMENT '附件id',
  `sender_id` int(10) NOT NULL COMMENT '发送者id',
  `receiver_id` int(10) NOT NULL COMMENT '收信者id',
  `doc_id` int(10) NOT NULL COMMENT '文档id',
  `send_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已读',
  `state` int(1) NULL DEFAULT NULL COMMENT '发送时间',
  `is_use` int(1) NULL DEFAULT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_msgs_users1_idx`(`sender_id`) USING BTREE,
  INDEX `fk_msgs_users2_idx`(`receiver_id`) USING BTREE,
  INDEX `fk_msgs_docs1_idx`(`doc_id`) USING BTREE,
  CONSTRAINT `fk_msgs_docs1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_msgs_users1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_msgs_users2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名',
  `color` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题色',
  `image_url` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品图片',
  `descibe` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品描述',
  `created_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `is_use` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for replies
-- ----------------------------
DROP TABLE IF EXISTS `replies`;
CREATE TABLE `replies`  (
  `id` int(100) NOT NULL COMMENT '回复id',
  `doc_id` int(10) NOT NULL COMMENT '文档id',
  `replier_id` int(10) NOT NULL COMMENT '回复者id',
  `reply_content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `reply_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复时间',
  `is_use` int(1) NULL DEFAULT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_enclosure_doc1_idx`(`doc_id`) USING BTREE,
  INDEX `fk_replies_users1_idx`(`replier_id`) USING BTREE,
  CONSTRAINT `fk_enclosure_doc10` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_replies_users1` FOREIGN KEY (`replier_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` int(10) NOT NULL COMMENT '标签id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `created_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `is_use` int(1) NOT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tagsfordocs
-- ----------------------------
DROP TABLE IF EXISTS `tagsfordocs`;
CREATE TABLE `tagsfordocs`  (
  `id` int(10) NOT NULL COMMENT '行号',
  `doc_id` int(10) NOT NULL COMMENT '文档id',
  `tag_id` int(10) NOT NULL COMMENT '标签id',
  `created_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `is_use` int(1) NULL DEFAULT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_tagForDoc_doc1_idx`(`doc_id`) USING BTREE,
  INDEX `fk_tagForDoc_tag1_idx`(`tag_id`) USING BTREE,
  CONSTRAINT `fk_tagForDoc_doc1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tagForDoc_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(10) NOT NULL COMMENT '用户id',
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `realname` varchar(155) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录令牌',
  `buildtime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌时间',
  `is_admin` int(1) NULL DEFAULT NULL COMMENT '是否为管理员 1为管理员，0为普通用户',
  `is_use` int(1) NULL DEFAULT NULL COMMENT '用于软删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
