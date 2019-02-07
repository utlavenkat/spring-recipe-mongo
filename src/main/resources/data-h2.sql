INSERT into category(id,category_name) values (1,'AMERICAN');
INSERT into category(id,category_name) values (2,'MEXICAN');
INSERT into category(id,category_name) values (3,'CHINESE');
INSERT into category(id,category_name) values (4,'FAST FOOD');

INSERT into unit_of_measure(id,uom) values (1,'Teaspoon');
INSERT into unit_of_measure(id,uom) values (2,'Tablespoon');
INSERT into unit_of_measure(id,uom) values (3,'Ounce');
INSERT into unit_of_measure(id,uom) values (4,'Cup');
INSERT into unit_of_measure(id,uom) values (5,'Pinch');
INSERT into unit_of_measure(id,uom) values (6,'Ripe');
INSERT into unit_of_measure(id,uom) values (7,'Each');
INSERT into unit_of_measure(id,uom) values (8,'Dash');
INSERT into unit_of_measure(id,uom) values (9,'Pint');

INSERT  into recipe(id,description) values (1,'Test Recipe');

INSERT into ingredient(id,description,amount,unit_of_measure_id) values (1,'Test Ingredient',2.0,1);