-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema 
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ssafytrip` ;

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb3 ;
USE `ssafytrip` ;

-- -----------------------------------------------------
-- Table `ssafytrip`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`admin` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`admin` (
  `adminid` INT NOT NULL,
  `id` VARCHAR(20) NULL DEFAULT NULL,
  `password` VARCHAR(30) NULL DEFAULT NULL,
  `nickname` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`adminid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`board` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`board` (
  `boardid` INT NOT NULL AUTO_INCREMENT,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` INT NULL DEFAULT '0',
  `title` VARCHAR(50) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `userid` INT NOT NULL,
  PRIMARY KEY (`boardid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`user` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`user` (
  `userid` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `nickname` VARCHAR(20) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `sido` VARCHAR(50) NULL DEFAULT NULL,
  `gugun` VARCHAR(50) NULL DEFAULT NULL,
  `roles` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`boardreview`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`boardreview` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`boardreview` (
  `boardreviewid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` TINYTEXT NOT NULL,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` INT NOT NULL,
  `boardid` INT NOT NULL,
  `user_userid` INT NOT NULL,
  PRIMARY KEY (`boardreviewid`),
  INDEX `fk_boardreview_board2_idx` (`boardid` ASC) VISIBLE,
  INDEX `fk_boardreview_user2_idx` (`user_userid` ASC) VISIBLE,
  CONSTRAINT `fk_boardreview_board2`
    FOREIGN KEY (`boardid`)
    REFERENCES `ssafytrip`.`board` (`boardid`),
  CONSTRAINT `fk_boardreview_user2`
    FOREIGN KEY (`user_userid`)
    REFERENCES `ssafytrip`.`user` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`hotplace`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`hotplace` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`hotplace` (
  `hotplaceid` INT NOT NULL AUTO_INCREMENT,
  `likecount` INT NULL DEFAULT '0',
  `placeid` INT NOT NULL,
  PRIMARY KEY (`hotplaceid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`hotplacedetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`hotplacedetail` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`hotplacedetail` (
  `userid` INT NOT NULL,
  `hotplaceid` INT NOT NULL,
  `user_userid` INT NOT NULL,
  PRIMARY KEY (`userid`, `hotplaceid`, `user_userid`),
  INDEX `fk_hotplacedetail_hotplace1_idx` (`hotplaceid` ASC) VISIBLE,
  INDEX `fk_hotplacedetail_user2_idx` (`user_userid` ASC) VISIBLE,
  CONSTRAINT `fk_hotplacedetail_hotplace1`
    FOREIGN KEY (`hotplaceid`)
    REFERENCES `ssafytrip`.`hotplace` (`hotplaceid`),
  CONSTRAINT `fk_hotplacedetail_user2`
    FOREIGN KEY (`user_userid`)
    REFERENCES `ssafytrip`.`user` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`hotplacereview`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`hotplacereview` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`hotplacereview` (
  `hotplacereviewid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` TINYTEXT NOT NULL,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `hotplaceid` INT NOT NULL,
  `userid` INT NOT NULL,
  `user_userid` INT NOT NULL,
  PRIMARY KEY (`hotplacereviewid`),
  INDEX `fk_hotplacereview_hotplace1_idx` (`hotplaceid` ASC) VISIBLE,
  INDEX `fk_hotplacereview_user2_idx` (`user_userid` ASC) VISIBLE,
  CONSTRAINT `fk_hotplacereview_hotplace1`
    FOREIGN KEY (`hotplaceid`)
    REFERENCES `ssafytrip`.`hotplace` (`hotplaceid`),
  CONSTRAINT `fk_hotplacereview_user2`
    FOREIGN KEY (`user_userid`)
    REFERENCES `ssafytrip`.`user` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`itinerary`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`itinerary` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`itinerary` (
  `itineraryid` INT NOT NULL AUTO_INCREMENT,
  `userid` INT NOT NULL,
  PRIMARY KEY (`itineraryid`),
  INDEX `fk_itinerary_user2_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_itinerary_user2`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafytrip`.`user` (`userid`)
    ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafytrip`.`itinerarydetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`itinerarydetail` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`itinerarydetail` (
  `orders` INT NOT NULL,
  `itineraryid` INT NOT NULL,
  `placeid` VARCHAR(40) NOT NULL,
  `userid` INT NOT NULL,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `startdate` DATE NULL,
  `enddate` DATE NULL,
  `title` VARCHAR(40) NULL,
  `content` VARCHAR(100) NULL,
  PRIMARY KEY (`orders`, `itineraryid`),
  INDEX `fk_itinerarydetail_itinerary1_idx` (`itineraryid` ASC) VISIBLE,
  INDEX `fk_itinerarydetail_user2_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_itinerarydetail_itinerary1`
    FOREIGN KEY (`itineraryid`)
    REFERENCES `ssafytrip`.`itinerary` (`itineraryid`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_itinerarydetail_user2`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafytrip`.`user` (`userid`)
    ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafytrip`.`itineraryreview`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`itineraryreview` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`itineraryreview` (
  `itineraryreviewid` INT NOT NULL AUTO_INCREMENT,
  `content` TINYTEXT NULL DEFAULT NULL,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `orders` INT NOT NULL,
  `itineraryid` INT NOT NULL,
  `userid` INT NOT NULL,
  PRIMARY KEY (`itineraryreviewid`),
  INDEX `fk_itineraryreview_itinerarydetail1_idx` (`orders` ASC, `itineraryid` ASC) VISIBLE,
  INDEX `fk_itineraryreview_user2_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_itineraryreview_itinerarydetail1`
    FOREIGN KEY (`orders`, `itineraryid`)
    REFERENCES `ssafytrip`.`itinerarydetail` (`orders`, `itineraryid`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_itineraryreview_user2`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafytrip`.`user` (`userid`)
    ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafytrip`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`member` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`member` (
  `user_id` VARCHAR(16) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `grade` ENUM('A', 'B', 'C') NULL DEFAULT NULL,
  `membercol` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`notice` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`notice` (
  `noticeid` INT NOT NULL AUTO_INCREMENT,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` INT NULL DEFAULT '0',
  `title` VARCHAR(50) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `adminid` INT NOT NULL,
  PRIMARY KEY (`noticeid`),
  INDEX `fk_notice_admin1_idx` (`adminid` ASC) VISIBLE,
  CONSTRAINT `fk_notice_admin1`
    FOREIGN KEY (`adminid`)
    REFERENCES `ssafytrip`.`admin` (`adminid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`orders` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`orders` (
  `order_id` INT NOT NULL,
  `orderdate` TIMESTAMP NULL DEFAULT NULL,
  `user_id` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_orders_member_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_member`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`member` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`product` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`product` (
  `productcode` VARCHAR(12) NOT NULL,
  `productname` VARCHAR(40) NULL DEFAULT NULL,
  `ea` INT NULL DEFAULT NULL,
  `price` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`productcode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`orderdetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`orderdetail` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`orderdetail` (
  `cnt` INT NULL DEFAULT NULL,
  `order_id` INT NOT NULL,
  `productcode` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`order_id`, `productcode`),
  INDEX `fk_orderdetail_product1_idx` (`productcode` ASC) VISIBLE,
  CONSTRAINT `fk_orderdetail_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ssafytrip`.`orders` (`order_id`),
  CONSTRAINT `fk_orderdetail_product1`
    FOREIGN KEY (`productcode`)
    REFERENCES `ssafytrip`.`product` (`productcode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`user_roles` (
  `user_id` VARCHAR(255) NOT NULL,
  `roles` VARCHAR(255) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
