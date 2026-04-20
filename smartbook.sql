/*
 Navicat Premium Data Transfer

 Source Server         : test_1
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : smartbook

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 10/04/2026 16:21:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for budget
-- ----------------------------
DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `period_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'WEEK / MONTH',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类预算，NULL表示总预算',
  `amount` decimal(12, 2) NOT NULL COMMENT '预算金额',
  `alert_threshold` int NOT NULL DEFAULT 80 COMMENT '预警阈值（%）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_category_period`(`user_id` ASC, `category_name` ASC, `period_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '预算表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for challenge_day
-- ----------------------------
DROP TABLE IF EXISTS `challenge_day`;
CREATE TABLE `challenge_day`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `challenge_id` bigint NOT NULL COMMENT 'challenge_record.id',
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `challenge_date` date NOT NULL COMMENT '完成日期',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '由当天首笔账单写入触发',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_challenge_date`(`challenge_id` ASC, `challenge_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '挑战每日完成记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for challenge_record
-- ----------------------------
DROP TABLE IF EXISTS `challenge_record`;
CREATE TABLE `challenge_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `start_date` date NOT NULL COMMENT '挑战开始日期',
  `completed_days` int NOT NULL DEFAULT 0 COMMENT '已完成天数',
  `badges` json NULL COMMENT '已解锁徽章列表，如 [\"junior_planner\"]',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=进行中 1=已完成 2=已放弃',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_status`(`user_id` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '21天挑战记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ocr_record
-- ----------------------------
DROP TABLE IF EXISTS `ocr_record`;
CREATE TABLE `ocr_record`  (
  `task_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'OCR任务ID',
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '识别图片地址',
  `recognized` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0=未识别 1=识别成功',
  `confidence` decimal(4, 2) NULL DEFAULT NULL COMMENT '置信度 0~1',
  `amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '识别金额',
  `merchant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '识别商户名',
  `occurred_at` datetime NULL DEFAULT NULL COMMENT '识别到的交易时间',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '建议分类名',
  `raw_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '原始识别文本',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=处理中 1=就绪 2=已确认入账 3=识别失败',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`) USING BTREE,
  INDEX `idx_user_created`(`user_id` ASC, `created_at` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OCR识别记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saving_goal
-- ----------------------------
DROP TABLE IF EXISTS `saving_goal`;
CREATE TABLE `saving_goal`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标名称',
  `target_amount` decimal(12, 2) NOT NULL COMMENT '目标金额',
  `current_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '当前已存金额',
  `target_date` date NULL DEFAULT NULL COMMENT '目标完成日期',
  `cover_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图片',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=进行中 1=已完成 2=已放弃',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '储蓄目标表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saving_goal_deposit
-- ----------------------------
DROP TABLE IF EXISTS `saving_goal_deposit`;
CREATE TABLE `saving_goal_deposit`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `goal_id` bigint NOT NULL COMMENT 'saving_goal.id',
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `amount` decimal(12, 2) NOT NULL COMMENT '本次存入金额',
  `occurred_at` datetime NOT NULL COMMENT '存入时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '储蓄目标存入记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shared_bill
-- ----------------------------
DROP TABLE IF EXISTS `shared_bill`;
CREATE TABLE `shared_bill`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账单名称',
  `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'CNY' COMMENT '货币',
  `total_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '总金额（由item汇总）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=未结算 1=已结算',
  `created_by` bigint NOT NULL COMMENT '创建人 user.id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '共享账单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shared_bill_item
-- ----------------------------
DROP TABLE IF EXISTS `shared_bill_item`;
CREATE TABLE `shared_bill_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shared_bill_id` bigint NOT NULL COMMENT 'shared_bill.id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消费名称',
  `amount` decimal(12, 2) NOT NULL COMMENT '总金额',
  `paid_by_user_id` bigint NOT NULL COMMENT '实际付款人 user.id',
  `split_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'AVERAGE' COMMENT 'AVERAGE=平均分摊 CUSTOM=自定义',
  `occurred_at` datetime NOT NULL COMMENT '消费时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bill_occurred`(`shared_bill_id` ASC, `occurred_at` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '共享账单消费记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shared_bill_item_member
-- ----------------------------
DROP TABLE IF EXISTS `shared_bill_item_member`;
CREATE TABLE `shared_bill_item_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `item_id` bigint NOT NULL COMMENT 'shared_bill_item.id',
  `user_id` bigint NOT NULL COMMENT '参与人 user.id',
  `owed_amount` decimal(12, 2) NOT NULL COMMENT '应付金额',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_item_user`(`item_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '共享账单分摊明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shared_bill_member
-- ----------------------------
DROP TABLE IF EXISTS `shared_bill_member`;
CREATE TABLE `shared_bill_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shared_bill_id` bigint NOT NULL COMMENT 'shared_bill.id',
  `user_id` bigint NOT NULL COMMENT 'user.id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '成员显示名',
  `joined_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_bill_user`(`shared_bill_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '共享账单成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shared_bill_settlement
-- ----------------------------
DROP TABLE IF EXISTS `shared_bill_settlement`;
CREATE TABLE `shared_bill_settlement`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shared_bill_id` bigint NOT NULL COMMENT 'shared_bill.id',
  `from_user_id` bigint NOT NULL COMMENT '付款方 user.id',
  `to_user_id` bigint NOT NULL COMMENT '收款方 user.id',
  `amount` decimal(12, 2) NOT NULL COMMENT '结算金额',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=待确认 1=已确认',
  `confirmed_at` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '共享账单结算记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for transactions
-- ----------------------------
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '记录人 user.id',
  `type` tinyint NOT NULL COMMENT '0=支出 1=收入',
  `amount` decimal(12, 2) NOT NULL COMMENT '金额，必须大于0',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名：餐饮/交通/购物/娱乐/医疗/收入/其他',
  `merchant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户名称',
  `occurred_at` datetime NOT NULL COMMENT '交易发生时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `receipt_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小票图片地址',
  `ocr_task_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'OCR任务ID，不为NULL则来自OCR识别',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=正常 1=已删除（逻辑删除）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_occurred`(`user_id` ASC, `occurred_at` ASC) USING BTREE,
  INDEX `idx_user_category`(`user_id` ASC, `category_name` ASC, `occurred_at` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号，登录凭证',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0=正常 1=禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mobile`(`mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
