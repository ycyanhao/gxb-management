-- ----------------------------
-- Table structure for `file_store_log`
-- ----------------------------
-- DROP TABLE IF EXISTS `file_store_log`; ---
CREATE TABLE `file_store_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` varchar(50) DEFAULT '0',
  `type` varchar(50) DEFAULT '0',
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `update_user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文件存储记录';

-- ----------------------------
-- Records of file_store_log
-- ----------------------------
INSERT INTO `file_store_log` VALUES ('1', '0', '0', '组件监控及告警设计.docx', 'group1/M00/00/00/wKhmwl4AaKGATWq4AANvBR7gT7o35.docx', null, '2019-12-23 01:11:32', null, 'N', null, null);
