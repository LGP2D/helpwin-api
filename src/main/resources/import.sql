INSERT INTO roles (description) VALUES ("ADMIN");
INSERT INTO roles (description) VALUES ("INSTITUTION");
INSERT INTO roles (description) VALUES ("VOLUNTEER");
INSERT INTO roles (description) VALUES ("COMPANY");

INSERT INTO institutions(description,imageUrl,name) values("ONG medica","http://test.com","MEDIS");
INSERT INTO institutions(description,imageUrl,name) values("SEM ABRIGOS","http://test2.com","CORACAO QUENTE");

#INSERT INTO actions(description,endDate,startDate,type,valid,institution_id) values("Action A","2017-08-01","2017-08-01","tipo test",true,1);
#INSERT INTO actions(description,endDate,startDate,type,valid,institution_id,verified,availablePosition) values("Action A","2017-08-01","2017-08-01","tipo test",true,1,false,10);

INSERT INTO vouchers (description, imagePath, company) VALUES ("voucher", "http://voxsingingacademy.com.au/wp-content/uploads/2012/11/gift-voucher.png", "fnac");
INSERT INTO vouchers (description, imagePath, company) VALUES ("voucher", "http://www.carloisles.com/project-cms/wp-content/uploads/2014/10/Takatack-Free-150-Shopping-Voucher-Coupon.png", "worten");
INSERT INTO vouchers (description, imagePath, company) VALUES ("voucher", "http://www.sonorous.tv/product_images/uploaded_images/gift-voucher.png", "adidas");
INSERT INTO vouchers (description, imagePath, company) VALUES ("voucher", "http://www.wordmstemplates.com/wp-content/uploads/2015/08/gift-voucher-template-1245.jpg", "nike");

#INSERT INTO vouchers(type,description,imagePath,startDate,endDate,quantity,credits,institution_id) VALUES ('Discount','10% Discount on all electronics products',"2017-08-01","2016-08-01",10,250,1);

#INSERT INTO users (id,name,birthDate,email,password,profession,imageUrl,role) VALUES (1,"Anna","1997-08-01","annatest@gmail.com", "123456", "student", "http://s7d2.scene7.com/is/image/PetSmart/PB0101_HERO-Dog-TreatsRawhide-20160818?$sclp-banner-main_large$", 3)