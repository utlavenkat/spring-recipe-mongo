## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE spring_recipe_dev;
CREATE DATABASE spring_recipe_prod;

#Create database service accounts
CREATE USER 'spring_recipe_dev_user'@'localhost' IDENTIFIED BY 'cute@ammu04';
CREATE USER 'spring_recipe_prod_user'@'localhost' IDENTIFIED BY 'cute@ammu04';
CREATE USER 'spring_recipe_dev_user'@'%' IDENTIFIED BY 'cute@ammu04';
CREATE USER 'spring_recipe_prod_user'@'%' IDENTIFIED BY 'cute@ammu04';

#Database grants
GRANT SELECT ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'localhost';
GRANT INSERT ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'localhost';
GRANT DELETE ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'localhost';
GRANT UPDATE ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'localhost';
GRANT SELECT ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'localhost';
GRANT INSERT ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'localhost';
GRANT DELETE ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'localhost';
GRANT UPDATE ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'localhost';
GRANT SELECT ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'%';
GRANT INSERT ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'%';
GRANT DELETE ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'%';
GRANT UPDATE ON spring_recipe_dev.* to 'spring_recipe_dev_user'@'%';
GRANT SELECT ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'%';
GRANT INSERT ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'%';
GRANT DELETE ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'%';
GRANT UPDATE ON spring_recipe_prod.* to 'spring_recipe_prod_user'@'%';
