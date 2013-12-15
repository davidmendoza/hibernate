SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

-- -----------------------------------------------------
-- Table `mydb`.`Department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Department` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Department` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `dept_name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EmployeeDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`EmployeeDetails` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`EmployeeDetails` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `first_name` VARCHAR(255) NOT NULL ,
  `last_name` VARCHAR(255) NOT NULL ,
  `age` INT NOT NULL ,
  `deptId` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_deptId` (`deptId` ASC) ,
  CONSTRAINT `fk_deptId`
    FOREIGN KEY (`deptId` )
    REFERENCES `mydb`.`Department` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Projects` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Projects` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `project_name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Employee_Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Employee_Project` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Employee_Project` (
  `employee_id` INT NOT NULL ,
  `project_id` INT NOT NULL ,
  INDEX `fk_empId` (`employee_id` ASC) ,
  INDEX `fk_projId` (`project_id` ASC) ,
  CONSTRAINT `fk_empId`
    FOREIGN KEY (`employee_id` )
    REFERENCES `mydb`.`EmployeeDetails` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projId`
    FOREIGN KEY (`project_id` )
    REFERENCES `mydb`.`Projects` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
