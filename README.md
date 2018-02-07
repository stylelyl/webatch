# webatch
spring batch project for scaling and parallel processing


1,This project used mysql database and database configuration in file src/main/resources/batch.properties. Please update as your env.
  And need to create two business tables:
  CREATE TABLE `tm_card_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(32) DEFAULT NULL,
  `CURR_BAL` decimal(10,0) DEFAULT NULL,
  `CARD_STATUS` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `UPDATE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
);
CREATE TABLE `key_context` (
  `CONTEXT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `KEY_LIST` blob,
  `SETUP_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CONTEXT_ID`)
);

in launch-context.xml, use jdbc:initialize-database to init all tables about spring batch 
<jdbc:initialize-database data-source="dataSource">
   <jdbc:script location="${batch.schema_drop.script}" /> <jdbc:script location="${batch.schema.script}" />
</jdbc:initialize-database> 

2, Populate some data to table tm_card_info, Then run main class com.test.webatch.App
