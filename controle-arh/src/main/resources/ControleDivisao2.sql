-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema arh
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema arh
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `arh` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `arh` ;

-- -----------------------------------------------------
-- Table `arh`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Funcionario` (
  `chave` VARCHAR(8) NOT NULL,
  `hash` VARCHAR(64) NULL,
  `trocar_senha` TINYINT(1) NULL,
  `nome` VARCHAR(100) NOT NULL,
  `equipe` INT NULL,
  `admin` TINYINT(1) NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`chave`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Tarefa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Tarefa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero` INT NULL,
  `acao` INT NULL,
  `esforco` INT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  `dt_ini_plan` DATETIME NULL,
  `dt_fim_plan` DATETIME NULL,
  `dt_ini_realizado` DATE NULL,
  `dt_fim_realizado` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Meta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Meta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  `dt_limite` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_Meta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Funcionario_has_Meta` (
  `Funcionario_chave` VARCHAR(8) NOT NULL,
  `Meta_id` INT NOT NULL,
  `atendido` TINYINT(1) NOT NULL,
  PRIMARY KEY (`Funcionario_chave`, `Meta_id`),
  INDEX `fk_Funcionario_has_Meta_Meta1_idx` (`Meta_id` ASC),
  INDEX `fk_Funcionario_has_Meta_Funcionario_idx` (`Funcionario_chave` ASC),
  CONSTRAINT `fk_Funcionario_has_Meta_Funcionario`
    FOREIGN KEY (`Funcionario_chave`)
    REFERENCES `arh`.`Funcionario` (`chave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Meta_Meta1`
    FOREIGN KEY (`Meta_id`)
    REFERENCES `arh`.`Meta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_Tarefa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Funcionario_has_Tarefa` (
  `Funcionario_chave` VARCHAR(8) NOT NULL,
  `Tarefa_id` INT NOT NULL,
  PRIMARY KEY (`Funcionario_chave`, `Tarefa_id`),
  INDEX `fk_Funcionario_has_Demanda_Funcionario1_idx` (`Funcionario_chave` ASC),
  INDEX `fk_Funcionario_has_Tarefa_Tarefa1_idx` (`Tarefa_id` ASC),
  CONSTRAINT `fk_Funcionario_has_Demanda_Funcionario1`
    FOREIGN KEY (`Funcionario_chave`)
    REFERENCES `arh`.`Funcionario` (`chave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Tarefa_Tarefa1`
    FOREIGN KEY (`Tarefa_id`)
    REFERENCES `arh`.`Tarefa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Inventario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `num_bem` VARCHAR(20) NOT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `num_bem_UNIQUE` (`num_bem` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Funcionario_has_Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Funcionario_has_Inventario` (
  `Funcionario_chave` VARCHAR(8) NOT NULL,
  `Inventario_id` INT NOT NULL,
  PRIMARY KEY (`Funcionario_chave`, `Inventario_id`),
  INDEX `fk_Funcionario_has_inventario_Funcionario1_idx` (`Funcionario_chave` ASC),
  INDEX `fk_Funcionario_has_Inventario_Inventario1_idx` (`Inventario_id` ASC),
  CONSTRAINT `fk_Funcionario_has_inventario_Funcionario1`
    FOREIGN KEY (`Funcionario_chave`)
    REFERENCES `arh`.`Funcionario` (`chave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Inventario_Inventario1`
    FOREIGN KEY (`Inventario_id`)
    REFERENCES `arh`.`Inventario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`impacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`impacto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `arh`.`Tarefa_has_impacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `arh`.`Tarefa_has_impacto` (
  `impacto_id` INT NOT NULL,
  `Tarefa_id` INT NOT NULL,
  PRIMARY KEY (`impacto_id`, `Tarefa_id`),
  INDEX `fk_Demanda_has_impacto_impacto1_idx` (`impacto_id` ASC),
  INDEX `fk_Demanda_has_impacto_Tarefa1_idx` (`Tarefa_id` ASC),
  CONSTRAINT `fk_Demanda_has_impacto_impacto1`
    FOREIGN KEY (`impacto_id`)
    REFERENCES `arh`.`impacto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Demanda_has_impacto_Tarefa1`
    FOREIGN KEY (`Tarefa_id`)
    REFERENCES `arh`.`Tarefa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


CREATE USER 'usrctrl'@'localhost' IDENTIFIED BY 'usrctrl';
GRANT ALL PRIVILEGES ON arh.* TO 'usrctrl'@'localhost';
INSERT INTO `arh`.`Funcionario` (`chave`, `hash`, `trocar_senha`, `nome`, `equipe`, `admin`, `ativo`) VALUES ('C1255240', 'E10ADC3949BA59ABBE56E057F20F883E', '1', 'Tiago Emerick', '1', '1', '1');