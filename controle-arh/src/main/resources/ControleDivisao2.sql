SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `arh` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `arh` ;

-- -----------------------------------------------------
-- Table `arh`.`Funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Funcionario` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Funcionario` (
  `chave` VARCHAR(8) NOT NULL ,
  `hash` VARCHAR(64) NULL ,
  `trocar_senha` TINYINT(1) NULL ,
  `nome` VARCHAR(100) NOT NULL ,
  `equipe` INT NULL ,
  PRIMARY KEY (`chave`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Demanda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Demanda` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Demanda` (
  `numero` INT NOT NULL ,
  `acao` INT NULL ,
  `esforco` INT NULL ,
  `descricao` VARCHAR(100) NOT NULL ,
  `dt_ini` DATETIME NULL ,
  `dt_fim` DATETIME NULL ,
  PRIMARY KEY (`numero`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Meta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Meta` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Meta` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_limite` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_Meta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Funcionario_has_Meta` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Funcionario_has_Meta` (
  `Funcionario_chave` VARCHAR(8) NOT NULL ,
  `Meta_id` INT NOT NULL ,
  `atendido` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`Funcionario_chave`, `Meta_id`) ,
  INDEX `fk_Funcionario_has_Meta_Meta1_idx` (`Meta_id` ASC) ,
  INDEX `fk_Funcionario_has_Meta_Funcionario_idx` (`Funcionario_chave` ASC) ,
  CONSTRAINT `fk_Funcionario_has_Meta_Funcionario`
    FOREIGN KEY (`Funcionario_chave` )
    REFERENCES `arh`.`Funcionario` (`chave` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Meta_Meta1`
    FOREIGN KEY (`Meta_id` )
    REFERENCES `arh`.`Meta` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_Demanda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Funcionario_has_Demanda` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Funcionario_has_Demanda` (
  `Funcionario_chave` VARCHAR(8) NOT NULL ,
  `Demanda_numero` INT NOT NULL ,
  PRIMARY KEY (`Funcionario_chave`, `Demanda_numero`) ,
  INDEX `fk_Funcionario_has_Demanda_Demanda1_idx` (`Demanda_numero` ASC) ,
  INDEX `fk_Funcionario_has_Demanda_Funcionario1_idx` (`Funcionario_chave` ASC) ,
  CONSTRAINT `fk_Funcionario_has_Demanda_Funcionario1`
    FOREIGN KEY (`Funcionario_chave` )
    REFERENCES `arh`.`Funcionario` (`chave` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Demanda_Demanda1`
    FOREIGN KEY (`Demanda_numero` )
    REFERENCES `arh`.`Demanda` (`numero` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`inventario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`inventario` ;

CREATE  TABLE IF NOT EXISTS `arh`.`inventario` (
  `num_bem` VARCHAR(20) NOT NULL ,
  `chave_funcionario` VARCHAR(8) NULL ,
  `descricao` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`num_bem`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_inventario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Funcionario_has_inventario` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Funcionario_has_inventario` (
  `Funcionario_chave` VARCHAR(8) NOT NULL ,
  `inventario_num_bem` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`Funcionario_chave`, `inventario_num_bem`) ,
  INDEX `fk_Funcionario_has_inventario_inventario1_idx` (`inventario_num_bem` ASC) ,
  INDEX `fk_Funcionario_has_inventario_Funcionario1_idx` (`Funcionario_chave` ASC) ,
  CONSTRAINT `fk_Funcionario_has_inventario_Funcionario1`
    FOREIGN KEY (`Funcionario_chave` )
    REFERENCES `arh`.`Funcionario` (`chave` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_inventario_inventario1`
    FOREIGN KEY (`inventario_num_bem` )
    REFERENCES `arh`.`inventario` (`num_bem` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`impacto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`impacto` ;

CREATE  TABLE IF NOT EXISTS `arh`.`impacto` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Demanda_has_impacto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `arh`.`Demanda_has_impacto` ;

CREATE  TABLE IF NOT EXISTS `arh`.`Demanda_has_impacto` (
  `Demanda_numero` INT NOT NULL ,
  `impacto_id` INT NOT NULL ,
  PRIMARY KEY (`Demanda_numero`, `impacto_id`) ,
  INDEX `fk_Demanda_has_impacto_impacto1_idx` (`impacto_id` ASC) ,
  INDEX `fk_Demanda_has_impacto_Demanda1_idx` (`Demanda_numero` ASC) ,
  CONSTRAINT `fk_Demanda_has_impacto_Demanda1`
    FOREIGN KEY (`Demanda_numero` )
    REFERENCES `arh`.`Demanda` (`numero` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Demanda_has_impacto_impacto1`
    FOREIGN KEY (`impacto_id` )
    REFERENCES `arh`.`impacto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `arh` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

CREATE USER 'usrctrl'@'localhost' IDENTIFIED BY 'usrctrl';
GRANT ALL PRIVILEGES ON arh.* TO 'usrctrl'@'localhost';