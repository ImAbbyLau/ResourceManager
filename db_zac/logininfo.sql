USE admindb;

CREATE TABLE logininfo (
username varchar(45) NOT NULL,
password varchar(45) NOT NULL,
usertype varchar(45) NOT NULL,
PRIMARY KEY(username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;

INSERT INTO `logininfo` VALUES ('user1', '111111', '高级'),('user2', '111111', '次高级'),('user3', '111111', '中级'),('user4', '111111', '低级');
