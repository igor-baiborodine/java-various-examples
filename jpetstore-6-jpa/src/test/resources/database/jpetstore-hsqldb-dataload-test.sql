INSERT INTO bannerdata VALUES ('DOGS','<image src="../images/banner_dogs.gif">');

INSERT INTO account(userid, version, email, firstname, lastname, status, addr1,
    addr2, city, state, zip, country, phone, favcategory)
  VALUES('j2ee', 0, 'yourname@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road',
    'MS UCUP02-555', 'Palo Alto', 'CA', '94303', 'USA',  '555-555-5555', 'DOGS');

INSERT INTO signon VALUES('j2ee','j2ee');

INSERT INTO profile(userid, langpref, favcategory, mylistopt, banneropt)
  VALUES('j2ee','english','DOGS',1,1);

INSERT INTO supplier(suppid, version, name, status, addr1, addr2, city, state, zip, phone)
  VALUES (1,0,'XYZ Pets','AC','600 Avon Way','','Los Angeles','CA','94024','212-947-0797');

INSERT INTO category(catid, name, descn) VALUES ('DOGS','Dogs','<image src="../images/dogs_icon.gif"><font size="5" color="blue"> Dogs</font>');

INSERT INTO product(productid, categoryid, name, descn) VALUES ('K9-BD-01','DOGS','Bulldog','<image src="../images/dog2.gif">Friendly dog from England');

INSERT INTO  item (itemid, productid, listprice, unitcost, supplierid, status, attr1) VALUES('EST-6','K9-BD-01',18.50,12.00,1,'P','Male Adult');

INSERT INTO inventory (itemid, qty ) VALUES ('EST-6',10000);
