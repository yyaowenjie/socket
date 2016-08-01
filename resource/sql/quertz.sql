CREATE TABLE `t_schedulejob` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(255) DEFAULT NULL,
  `jobGroup` varchar(255) DEFAULT NULL,
  `jobStatus` varchar(255) DEFAULT '1',
  `cronExpression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `springId` varchar(255) DEFAULT NULL,
  `beanClass` varchar(255) DEFAULT NULL,
  `isConcurrent` varchar(255) DEFAULT '0',
  `methodName` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=919171 DEFAULT CHARSET=utf8;


INSERT into t_schedulejob(jobName,jobGroup,jobStatus,cronExpression,description,springId,
beanClass,isConcurrent,methodName,createTime,updateTime) 
values('job2','group2','1','15 0/2 * * * ?','qazz',null,'com.sq.quert.service.SchedulerExecuteService','0','endQuertzCollection','2016-07-20 13:00:01','2016-07-20 13:00:01');
