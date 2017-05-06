INSERT INTO roles (description) VALUES ("ADMIN");
INSERT INTO roles (description) VALUES ("INSTITUTION");
INSERT INTO roles (description) VALUES ("VOLUNTEER");
INSERT INTO roles (description) VALUES ("COMPANY");

INSERT INTO institutions(description,imageUrl,name) values("ONG medica","http://test.com","MEDIS");
INSERT INTO institutions(description,imageUrl,name) values("SEM ABRIGOS","http://test2.com","CORACAO QUENTE");

INSERT INTO actions(description,endDate,startDate,type,valid,institution_id) values("Action A","2017-08-01","2017-08-01","tipo test",true,1);

INSERT INTO vouchers (description, imagePath, company) VALUES ("voucher", "path", "fnac");