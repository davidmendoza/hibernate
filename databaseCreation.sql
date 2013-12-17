SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `EmployeeProfile` ;
CREATE SCHEMA IF NOT EXISTS `EmployeeProfile` DEFAULT CHARACTER SET latin1 COLLATE latin1_danish_ci ;
USE `EmployeeProfile` ;

-- -----------------------------------------------------
-- Table `EmployeeProfile`.`Department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EmployeeProfile`.`Department` ;

CREATE  TABLE IF NOT EXISTS `EmployeeProfile`.`Department` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `dept_name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EmployeeProfile`.`EmployeeDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EmployeeProfile`.`EmployeeDetails` ;

CREATE  TABLE IF NOT EXISTS `EmployeeProfile`.`EmployeeDetails` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `first_name` VARCHAR(255) NOT NULL ,
  `last_name` VARCHAR(255) NOT NULL ,
  `gender` VARCHAR(10) NOT NULL,
  `age` INT NOT NULL ,
  `deptId` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_deptId` (`deptId` ASC) ,
  CONSTRAINT `fk_deptId`
    FOREIGN KEY (`deptId` )
    REFERENCES `EmployeeProfile`.`Department` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EmployeeProfile`.`Projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EmployeeProfile`.`Projects` ;

CREATE  TABLE IF NOT EXISTS `EmployeeProfile`.`Projects` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `project_name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EmployeeProfile`.`Employee_Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EmployeeProfile`.`Employee_Project` ;

CREATE  TABLE IF NOT EXISTS `EmployeeProfile`.`Employee_Project` (
  `employee_id` INT NOT NULL ,
  `project_id` INT NOT NULL ,
  INDEX `fk_empId` (`employee_id` ASC) ,
  INDEX `fk_projId` (`project_id` ASC) ,
  CONSTRAINT `fk_empId`
    FOREIGN KEY (`employee_id` )
    REFERENCES `EmployeeProfile`.`EmployeeDetails` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projId`
    FOREIGN KEY (`project_id` )
    REFERENCES `EmployeeProfile`.`Projects` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
