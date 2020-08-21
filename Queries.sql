CREATE SCHEMA `coronakitdb_KB` ;

use `coronakitdb_KB`;

CREATE TABLE `coronakitdb_KB`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(100) NOT NULL,
  `product_desc` VARCHAR(250) NOT NULL,
  `product_cost` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC) VISIBLE,
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC) VISIBLE);

INSERT INTO `coronakitdb_KB`.`products` ( product_name, product_desc, product_cost) VALUES ('Face-Mask-NR', 'Non Reusable Face Mask', 15.00);
INSERT INTO `coronakitdb_KB`.`products` ( product_name, product_desc, product_cost) VALUES ('Face-Mask-RC', 'Cloth Reusable Face Mask', 40.00);
INSERT INTO `coronakitdb_KB`.`products` ( product_name, product_desc, product_cost) VALUES ('Face-Shield-Small', 'Face sheild small size suitale for kids age 3 to 7 years', 150.00);
INSERT INTO `coronakitdb_KB`.`products` ( product_name, product_desc, product_cost) VALUES ('Face-Shield-Medium', 'Face sheild Medium size', 175.00);
INSERT INTO `coronakitdb_KB`.`products` ( product_name, product_desc, product_cost) VALUES ('Face-Shield-Regular', 'Face sheild free size', 210.00);

CREATE TABLE `coronakitdb_KB`.`coronakit` (
  `id` INT NOT NULL,
  `personname` VARCHAR(100) NOT NULL,
  `personalemail` VARCHAR(100) NOT NULL,
  `personmobile` VARCHAR(20) NOT NULL,
  `personaddress` VARCHAR(1000) NOT NULL,
  `orderamount` DOUBLE NOT NULL,
  `orderdate` VARCHAR(45) NOT NULL,
  `orderconfirmed` TINYINT NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `coronakitdb_KB`.`kitdetails` (
  `kitid` INT NOT NULL AUTO_INCREMENT,
  `coronakitid` INT NOT NULL,
  `productid` INT NOT NULL,
  `quantity` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  PRIMARY KEY (`kitid`),
  INDEX `fk_coronaidkey_idx` (`coronakitid` ASC) VISIBLE,
  INDEX `fk_productid_idx` (`productid` ASC) VISIBLE,
  UNIQUE INDEX `kitid_UNIQUE` (`kitid` ASC) VISIBLE,
  CONSTRAINT `fk_coronaidkey`
    FOREIGN KEY (`coronakitid`)
    REFERENCES `coronakitdb_KB`.`coronakit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

