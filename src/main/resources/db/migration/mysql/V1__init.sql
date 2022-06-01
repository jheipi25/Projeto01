CREATE TABLE `coordinate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `instant` datetime NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `vehicle_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assigned_vehicle` bigint(20) DEFAULT NULL,
  `route_plan` varchar(255) NOT NULL,
  `route_status` varchar(255) NOT NULL,
  `update_status_finished_date` datetime DEFAULT NULL,
  `update_status_progress_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `stop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delivery_radius` double NOT NULL,
  `description` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `stop_status` varchar(255) NOT NULL,
  `update_status_finished_date` datetime DEFAULT NULL,
  `update_status_progress_date` datetime DEFAULT NULL,
  `id_route` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe7v63ubbi93nf3lefrkt8hj2l` (`id_route`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

ALTER TABLE `stop`
  ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`);