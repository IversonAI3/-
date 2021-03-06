DROP DATABASE `library`;
CREATE DATABASE `library`;
USE library;

DROP TABLE IF EXISTS `penalty`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `card`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `BorrowRecord`;
DROP TABLE IF EXISTS `ReturnRecord`;
DROP TABLE IF EXISTS `usertype`;

-- 默认是学生，学生可借8本书，21天，教师可借10本书，14天
CREATE TABLE `UserType`(
	`type_id` TINYINT PRIMARY KEY AUTO_INCREMENT,
	`type_name` VARCHAR(20),
	`quota` TINYINT DEFAULT 8,
	`days` TINYINT DEFAULT 21
) AUTO_INCREMENT = 1;

CREATE TABLE `Admin`(
	`admin_id` INT PRIMARY KEY AUTO_INCREMENT, 
	`account` VARCHAR(20) UNIQUE NOT NULL, -- 账号
	`name` VARCHAR(20) NOT NULL, -- 姓名
	`password` VARCHAR(20) NOT NULL,
	`regitime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP -- 注册时间
) AUTO_INCREMENT = 1;

CREATE TABLE `Book`(
	`book_id` INT PRIMARY KEY AUTO_INCREMENT,
	`title` VARCHAR(20) NOT NULL,
	`author` VARCHAR(20) DEFAULT 'unknown',
	`price` NUMERIC(5,2) DEFAULT 0.0 CHECK(price>=0.0),
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,-- 添加时间
	`modify_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 修改时间
	`quantity` INT DEFAULT 1 CHECK(quantity>=0)
) AUTO_INCREMENT = 1;

CREATE TABLE `Card`(
	`card_id` INT PRIMARY KEY AUTO_INCREMENT,
	`balance` NUMERIC(5,2) DEFAULT 100.0 CHECK(balance BETWEEN 0 AND 100), 
	`quota` TINYINT DEFAULT 8 CHECK(quota BETWEEN 0 AND 10)
) AUTO_INCREMENT = 1000000;

CREATE TABLE `User`(
	`user_id` INT PRIMARY KEY AUTO_INCREMENT,
	`account` VARCHAR(20) UNIQUE NOT NULL,
	`name` VARCHAR(20),
	`password` VARCHAR(20) NOT NULL,
	`regitime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 注册时间
	`card_id` INT,
	`type_id` TINYINT NOT NULL,
	FOREIGN KEY (type_id) REFERENCES UserType(type_id),
	FOREIGN KEY (card_id) REFERENCES Card(card_id)
) AUTO_INCREMENT = 1;

CREATE TABLE `BorrowRecord`(
	`book_id` INT NOT NULL,
	`card_id` INT NOT NULL,
	`borrow_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`return_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(book_id, card_id, borrow_time),
	FOREIGN KEY (book_id) REFERENCES Book(book_id),
	FOREIGN KEY (card_id) REFERENCES Card(card_id)
) AUTO_INCREMENT 1;

CREATE TABLE `ReturnRecord`(
	`return_id` INT PRIMARY KEY AUTO_INCREMENT,
	`book_id` INT NOT NULL,
	`card_id` INT NOT NULL,
	`borrow_time` DATETIME NOT NULL,
	`return_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 实际还书时间
	FOREIGN KEY (book_id, card_id, borrow_time)
	REFERENCES BorrowRecord(book_id, card_id, borrow_time)
) AUTO_INCREMENT = 1;


CREATE TABLE `Penalty`(
	`penalty_id` INT PRIMARY KEY AUTO_INCREMENT,
	`book_id` INT NOT NULL,
	`card_id` INT NOT NULL,
	`borrow_time` DATETIME NOT NULL,
	`return_id` INT NOT NULL,
	`fine` NUMERIC(5,2) NOT NULL, -- 罚款金额
	FOREIGN KEY (book_id, card_id, borrow_time) REFERENCES BorrowRecord(book_id, card_id, borrow_time),
	FOREIGN KEY (return_id) REFERENCES ReturnRecord(return_id)
) AUTO_INCREMENT 1;

											-- 卡号 余额 借书限额
INSERT INTO `card`VALUES(DEFAULT, DEFAULT, 3);


-- 类型编号，类型名， 限额
INSERT INTO usertype VALUES(DEFAULT, '学生', 8,  21);
INSERT INTO usertype VALUES(DEFAULT, '教师', 10, 14);


-- user_id 账号 密码 姓名 注册时间 卡号 用户类型
INSERT INTO `user` VALUES(NULL, 'user1','user1','user1', DEFAULT, NULL, 1);
INSERT INTO `user`(account, `password`, type_id, card_id) VALUES('user', 'user',1,1000000);


-- 											书id  书名 作者 价格 添加时间 修改时间 数量
INSERT INTO book VALUES(NULL, 'Java编程思想','埃克尔',87.5, DEFAULT, DEFAULT, 52);
INSERT INTO book VALUES(NULL, '演员的自我修养','斯坦尼斯拉夫斯基',99.99,DEFAULT, DEFAULT, 2);
INSERT INTO book VALUES(NULL, '时间简史','斯蒂芬·霍金',48,DEFAULT, DEFAULT, 6);
INSERT INTO book VALUES(NULL, '上下五千年',DEFAULT,198,DEFAULT, DEFAULT, DEFAULT);
INSERT INTO book VALUES(NULL, '史说岛争','张召忠',39.6,DEFAULT, DEFAULT, 5);
INSERT INTO book VALUES(NULL, '宇宙超度指南','李诞',68.63, DEFAULT, DEFAULT,2);
INSERT INTO book VALUES(NULL, '白鹿原','陈忠实',44.1, DEFAULT, DEFAULT,5);
INSERT INTO book VALUES(NULL, 'Java核心内容',DEFAULT,72.5, DEFAULT, DEFAULT, 52);
INSERT INTO book VALUES(NULL, 'Head First Java','ABC',47.5, DEFAULT, DEFAULT, 52);
INSERT INTO book VALUES(NULL, 'JVM详解',DEFAULT,75.5, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO book VALUES(NULL, '十万个为什么',DEFAULT,77.5, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO book VALUES(NULL, '浮生六记','沈复',21.6,DEFAULT, DEFAULT, 10);
INSERT INTO book VALUES(NULL, '三体','刘慈欣',77.5,DEFAULT, DEFAULT, 3);
INSERT INTO book VALUES(NULL, '白夜行','东野圭吾',41.1,DEFAULT, DEFAULT, 5);
INSERT INTO book VALUES(NULL, '解忧杂货店','东野圭吾',98,DEFAULT, DEFAULT, DEFAULT);
INSERT INTO book VALUES(NULL, '指尖上的中国',DEFAULT,37.6,DEFAULT, DEFAULT, 5);
INSERT INTO book VALUES(NULL, '我爱幼儿园',DEFAULT,33,DEFAULT, DEFAULT, DEFAULT);

											-- 编号 用户名 姓名 密码 注册时间
INSERT INTO `admin` VALUES(DEFAULT, 'admin', '陶怡', 'admin', DEFAULT);


-- 借阅记录 			        书号 卡号 借时间 还时间
INSERT INTO `borrowrecord` VALUES(1, 1000000, DEFAULT, DEFAULT);
INSERT INTO `borrowrecord` VALUES(1, 1000000, '2018-04-1 19:31:01', '2018-04-22 19:31:01');
INSERT INTO `borrowrecord` VALUES(1, 1000000, '2018-04-2 19:31:01', '2018-04-23 19:31:01');
INSERT INTO `borrowrecord` VALUES(1, 1000000, '2018-04-3 19:31:01', '2018-04-24 19:31:01');
INSERT INTO `borrowrecord` VALUES(1, 1000000, '2018-04-4 19:31:01', '2018-04-25 19:31:01');

-- 还书记录


-- 罚款记录

-- 存储过程
-- 查询所有的(没有归还的)借书记录,也就是换书记录中不存在
DROP PROCEDURE IF EXISTS borrowRecords;
DELIMITER $$
CREATE PROCEDURE borrowRecords(IN id INT)
BEGIN
	SELECT b.book_id, b.title, b.author, b.price, r.borrow_time, r.return_time 
	FROM `book` b 
	INNER JOIN `borrowrecord` r ON b.book_id = r.book_id 
	INNER JOIN `user` u ON u.card_id = r.card_id
	WHERE u.user_id=id
	AND NOT EXISTS 
	(SELECT 1 FROM returnrecord WHERE (r.book_id, r.card_id, r.borrow_time) = (book_id,card_id,borrow_time));
END$$
DELIMITER ;

-- 查询某个用户短时间内是否已经结果相同的书
DROP PROCEDURE IF EXISTS checkBorrow;
DELIMITER $$
CREATE PROCEDURE checkBorrow(IN bid INT, IN cid INT, IN btime TIMESTAMP)
BEGIN
	SELECT * FROM borrowrecord
	WHERE book_id = bid AND card_id=cid AND borrow_time = btime;
END$$
DELIMITER ;

-- 查询一个用户是否已经拥有借书卡
DROP PROCEDURE IF EXISTS checkCard;
DELIMITER $$
CREATE PROCEDURE checkCard(IN uid INT)
BEGIN
	SELECT card_id FROM `user`
	WHERE user_id = uid;
END$$
-- CALL checkCard(1);
DELIMITER ;

-- 查询所有已经归还的借书记录
DROP PROCEDURE IF EXISTS returnedRecord;
DELIMITER $$
CREATE PROCEDURE returnedRecord()
BEGIN
	SELECT * FROM borrowrecord b 
	WHERE EXISTS 
	(SELECT return_id FROM returnrecord r WHERE r.book_id=b.book_id AND r.card_id=b.card_id AND r.borrow_time=b.borrow_time);
END$$
DELIMITER ;

-- call returnedrecord();

-- 查询所有尚未归还的借书记录
DROP PROCEDURE IF EXISTS unreturnedRecord;
DELIMITER $$
CREATE PROCEDURE unreturnedRecord()
BEGIN
	SELECT * FROM borrowrecord b 
	WHERE NOT EXISTS 
	(SELECT return_id FROM returnrecord  r WHERE r.book_id=b.book_id AND r.card_id=b.card_id AND r.borrow_time=b.borrow_time);
END$$
-- call unreturnedRecord();
DELIMITER ;

-- 函数days()
-- 参数returnTime: 应该还书的时间
-- 参数returnedTime: 实际还书的时间
DROP FUNCTION IF EXISTS `days`;
DELIMITER $$
CREATE FUNCTION days(returnTime TIMESTAMP, returnedTime TIMESTAMP)
RETURNS INT
BEGIN
	DECLARE d INT;
	SELECT DATEDIFF(returnedTime, returnTime) INTO d;
	RETURN d;
END$$
DELIMITER ;

-- 测试函数days()
-- SELECT days('2018-05-12 14:48:54','2018-05-25 14-48-53');
-- SELECT datediff('2018-05-12 14:48:54','2018-05-25 14-48-53');
