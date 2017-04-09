-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema clinic_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `clinic_db` ;

-- -----------------------------------------------------
-- Schema clinic_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clinic_db` DEFAULT CHARACTER SET utf8 ;
USE `clinic_db` ;

-- -----------------------------------------------------
-- Table `clinic_db`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clinic_db`.`appointment` (
  `doctor_id` INT(10) UNSIGNED NOT NULL,
  `client_id` INT(10) UNSIGNED NOT NULL,
  `event_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`event_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `clinic_db`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clinic_db`.`client` (
  `client_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `clinic_db`.`doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clinic_db`.`doctor` (
  `doctor_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(60) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `username` VARCHAR(45) NOT NULL, 
  PRIMARY KEY (`doctor_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `clinic_db`.`events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clinic_db`.`events` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `time_start` TIME NOT NULL,
  `time_end` TIME NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `clinic_db`.`secretary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clinic_db`.`secretary` (
  `sec_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(60) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`sec_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
