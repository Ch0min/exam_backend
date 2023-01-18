-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema startcode
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `startcode`;

-- -----------------------------------------------------
-- Schema startcode
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `startcode` DEFAULT CHARACTER SET utf8mb3;
USE `startcode`;

-- -----------------------------------------------------
-- Table `startcode`.`house`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`house`;

CREATE TABLE IF NOT EXISTS `startcode`.`house`
(
    `house_id`        INT          NOT NULL AUTO_INCREMENT,
    `house_address`   VARCHAR(255) NOT NULL,
    `house_city`      VARCHAR(255) NOT NULL,
    `number_of_rooms` INT          NOT NULL,
    PRIMARY KEY (`house_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`rental`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`rental`;

CREATE TABLE IF NOT EXISTS `startcode`.`rental`
(
    `rental_id`             INT          NOT NULL AUTO_INCREMENT,
    `rental_contact_person` VARCHAR(255) NOT NULL,
    `rental_deposit`        INT          NOT NULL,
    `rental_end_date`       VARCHAR(255) NOT NULL,
    `rental_price_annual`   INT          NOT NULL,
    `rental_start_date`     VARCHAR(255) NOT NULL,
    `house_id`              INT          NOT NULL,
    PRIMARY KEY (`rental_id`),
    INDEX `FK_rental_house_id` (`house_id` ASC) VISIBLE,
    CONSTRAINT `FK_rental_house_id`
        FOREIGN KEY (`house_id`)
            REFERENCES `startcode`.`house` (`house_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`roles`;

CREATE TABLE IF NOT EXISTS `startcode`.`roles`
(
    `role_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`role_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`user`;

CREATE TABLE IF NOT EXISTS `startcode`.`user`
(
    `user_name`  VARCHAR(25)  NOT NULL,
    `user_email` VARCHAR(255) NULL DEFAULT NULL,
    `user_pass`  VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`user_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`tenant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`tenant`;

CREATE TABLE IF NOT EXISTS `startcode`.`tenant`
(
    `tenant_id`    INT          NOT NULL AUTO_INCREMENT,
    `tenant_job`   VARCHAR(255) NOT NULL,
    `tenant_name`  VARCHAR(255) NOT NULL,
    `tenant_phone` INT          NOT NULL,
    `user_name`    VARCHAR(25)  NULL DEFAULT NULL,
    PRIMARY KEY (`tenant_id`),
    INDEX `FK_tenant_user_name` (`user_name` ASC) VISIBLE,
    CONSTRAINT `FK_tenant_user_name`
        FOREIGN KEY (`user_name`)
            REFERENCES `startcode`.`user` (`user_name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`tenant_rental`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`tenant_rental`;

CREATE TABLE IF NOT EXISTS `startcode`.`tenant_rental`
(
    `rental_id` INT NOT NULL,
    `tenant_id` INT NOT NULL,
    PRIMARY KEY (`rental_id`, `tenant_id`),
    INDEX `FK_tenant_rental_tenant_id` (`tenant_id` ASC) VISIBLE,
    CONSTRAINT `FK_tenant_rental_rental_id`
        FOREIGN KEY (`rental_id`)
            REFERENCES `startcode`.`rental` (`rental_id`),
    CONSTRAINT `FK_tenant_rental_tenant_id`
        FOREIGN KEY (`tenant_id`)
            REFERENCES `startcode`.`tenant` (`tenant_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`user_roles`;

CREATE TABLE IF NOT EXISTS `startcode`.`user_roles`
(
    `role_name` VARCHAR(20) NOT NULL,
    `user_name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`role_name`, `user_name`),
    INDEX `FK_user_roles_user_name` (`user_name` ASC) VISIBLE,
    CONSTRAINT `FK_user_roles_role_name`
        FOREIGN KEY (`role_name`)
            REFERENCES `startcode`.`roles` (`role_name`),
    CONSTRAINT `FK_user_roles_user_name`
        FOREIGN KEY (`user_name`)
            REFERENCES `startcode`.`user` (`user_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `startcode`.`tenant_house`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `startcode`.`tenant_house`;

CREATE TABLE IF NOT EXISTS `startcode`.`tenant_house`
(
    `tenant_id` INT NOT NULL,
    `house_id`  INT NOT NULL,
    PRIMARY KEY (`tenant_id`, `house_id`),
    INDEX `fk_tenant_has_house_house1_idx` (`house_id` ASC) VISIBLE,
    INDEX `fk_tenant_has_house_tenant1_idx` (`tenant_id` ASC) VISIBLE,
    CONSTRAINT `fk_tenant_has_house_tenant1`
        FOREIGN KEY (`tenant_id`)
            REFERENCES `startcode`.`tenant` (`tenant_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tenant_has_house_house1`
        FOREIGN KEY (`house_id`)
            REFERENCES `startcode`.`house` (`house_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
