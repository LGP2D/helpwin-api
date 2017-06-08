#ROLES
INSERT INTO roles (description) VALUES ("ADMIN");
INSERT INTO roles (description) VALUES ("INSTITUTION");
INSERT INTO roles (description) VALUES ("VOLUNTEER");
INSERT INTO roles (description) VALUES ("COMPANY");

#EVALUATION_STATUS
INSERT INTO evaluation_status(description) VALUES ("PENDING");
INSERT INTO evaluation_status(description) VALUES ("SUCCESS");
INSERT INTO evaluation_status(description) VALUES ("FAILED");
INSERT INTO evaluation_status(description) VALUES ("REJECTED");

#INSERT INTO users (id,name,birthDate,email,password,profession,imageUrl,role) VALUES (1,"Anna","1997-08-01","annatest@gmail.com", "123456", "student", "http://s7d2.scene7.com/is/image/PetSmart/PB0101_HERO-Dog-TreatsRawhide-20160818?$sclp-banner-main_large$", 3);
#INSERT INTO actions(description,endDate,startDate,type,isActive,institution_id) values("Action A","2017-08-01","2017-08-01","tipo test",true,1);
#INSERT INTO actions(description,endDate,startDate,type,isActive,institution_id,verified,availablePosition) values("Action A","2017-08-01","2017-08-01","tipo test",true,1,false,10);


