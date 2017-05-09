INSERT INTO roles (description) VALUES ("ADMIN");
INSERT INTO roles (description) VALUES ("INSTITUTION");
INSERT INTO roles (description) VALUES ("VOLUNTEER");
INSERT INTO roles (description) VALUES ("COMPANY");

INSERT INTO institutions(description,imageUrl,name) values("ONG medica","http://test.com","MEDIS");
INSERT INTO institutions(description,imageUrl,name) values("SEM ABRIGOS","http://test2.com","CORACAO QUENTE");

INSERT INTO actions(description,endDate,startDate,type,valid,institution_id,verified,availablePosition) values("Action A","2017-08-01","2017-08-01","tipo test",true,1,false,10);

INSERT INTO vouchers(type,description,startDate,endDate,quantity,credits,institution_id) VALUES ('Discount','10% Discount on all electronics products',"2017-08-01","2016-08-01",10,250,1);