INSERT INTO roles (description) VALUES ("ADMIN");
INSERT INTO roles (description) VALUES ("GUEST");
INSERT INTO roles (description) VALUES ("VOLUNTEER");
INSERT INTO roles (description) VALUES ("COMPANY");

INSERT INTO helpwin.companies (email, name, password, vouchers) VALUES ('company@email.com', 'company', '123', null);

INSERT INTO helpwin.vouchers (description, imageURL, company_id) VALUES ('voucher', 'path', 1);