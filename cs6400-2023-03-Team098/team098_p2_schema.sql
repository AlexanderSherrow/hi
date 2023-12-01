drop database cs6400;
create database cs6400;
use cs6400;

CREATE TABLE User (
username varchar(250),
password varchar(250) NOT NULL,
first_name varchar(250) NOT NULL,
last_name varchar(250) NOT NULL,
PRIMARY KEY (username)
);

CREATE TABLE Owner (
username varchar(250),
PRIMARY KEY (username),
FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE Manager (
username varchar(250),
PRIMARY KEY (username),
FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE Salespeople (
username varchar(250),
PRIMARY KEY (username),
FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE InventoryClerk (
username varchar(250),
PRIMARY KEY (username),
FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE Customer (
state varchar(250) NOT NULL,
city varchar(250) NOT NULL,
street varchar(250) NOT NULL,
postal_code varchar(250) NOT NULL,
phone_number varchar(250) NOT NULL,
email varchar(250)  DEFAULT NULL,
business_tax_id varchar(250),
drivers_license_number varchar(250),
PRIMARY KEY (business_tax_id, drivers_license_number)
);

CREATE TABLE Business (
business_tax_id varchar(250) NOT NULL,
business_name varchar(250) NOT NULL,
contact_title varchar(250) NOT NULL,
first_name varchar(250) NOT NULL,
last_name varchar(250) NOT NULL,
drivers_license_number varchar(250),
PRIMARY KEY (business_tax_id),
foreign key (business_tax_id, drivers_license_number) references Customer(business_tax_id, drivers_license_number)
);

CREATE TABLE Individual (
drivers_license_number varchar(250) NOT NULL,
first_name varchar(250) NOT NULL,
last_name varchar(250) NOT NULL,
business_tax_id varchar(250),
PRIMARY KEY (drivers_license_number),
foreign key (business_tax_id, drivers_license_number) references Customer(business_tax_id, drivers_license_number)
);

CREATE TABLE Manufacturer (
name varchar(250),
PRIMARY KEY (name)
);

Insert into Manufacturer (name)
values 
('Acura'),( 'FIAT'),( 'Lamborghini'),
( 'Nio'),( 'Alfa Romeo'),( 'Ford'),
( 'Porsche'),( 'Aston Martin'),( 'Geeley'),
( 'Lexus'),( 'Ram'),( 'Audi'),( 'Genesis'),
( 'Lincoln'),( 'Rivian'),( 'Bentley'),
( 'GMC'),( 'Lotus'),( 'Rolls-Royce'),
( 'BMW'),( 'Honda'),( 'Maserati'),( 'smart'),
( 'Buick'),( 'Hyundai'),( 'MAZDA'),( 'Subaru'),
( 'Cadillac'),( 'INFINITI'),( 'McLaren'),( 'Tesla'),
( 'Chevrolet'),( 'Jaguar'),( 'Mercedes-Benz'),( 'Toyota'),
( 'Chrysler'),( 'Jeep'),( 'MINI'),( 'Land Rover'),
( 'Volkswagen'),( 'Dodge'),( 'Karma'),( 'Mitsubishi'),
( 'Volvo'),( 'Ferrari'),( 'Kia'),( 'Nissan'),( 'XPeng')
;

CREATE TABLE ChassisType (
name varchar(250),
PRIMARY KEY (name)
);

Insert into ChassisType (name)
values 
 ('Sedan'), ('Coupe'), ('Convertible'), ('Truck'), ('Van'), ('Minivan'), ('SUV'), ('Other')
;

CREATE TABLE FuelType (
name varchar(250),
PRIMARY KEY (name)
);

Insert into FuelType (name)
values 
 ('Gas'), ('Diesel'), ('Natural Gas'), ('Hybrid'), ('Plugin Hybrid'), ('Battery'), ('Fuel Cell')
;

CREATE TABLE Vehicle (
model_name varchar(250) NOT NULL,
model_year int(4)NOT NULL,
description varchar(250) default NULL,
vin varchar(250) NOT NULL,
mileage int(7) NOT NULL,
manufacturer varchar(250) NOT NULL,
fuel_type varchar(250) NOT NULL,
chassis_type varchar(250) NOT NULL,
PRIMARY KEY (vin),
foreign key(manufacturer) references Manufacturer(name),
foreign key(fuel_type) references FuelType(name),
foreign key(chassis_type) references ChassisType(name)
);

CREATE TABLE Color(
vin varchar(250) NOT NULL,
color ENUM('Aluminum', 'Beige', 'Black', 'Blue', 'Brown', 'Bronze', 'Claret', 'Copper', 'Cream', 'Gold', 'Gray', 'Green', 'Maroon', 'Metallic', 'Navy', 'Orange', 'Pink', 'Purple', 'Red', 'Rose', 'Rust', 'Silver', 'Tan', 'Turquoise', 'White', 'Yellow'),
PRIMARY KEY (vin, color),
FOREIGN KEY (vin) REFERENCES Vehicle(vin)
);

CREATE TABLE Sold (
vin varchar(250),
vehicle_condition ENUM ('Fair', 'Good', 'Very Good', 'Excellent'),
purchase_price FLOAT(24) NOT NULL,
purchase_date datetime NOT NULL,
business_tax_id varchar(250),
drivers_license_number varchar(250),
inventoryClerk varchar(250),
PRIMARY KEY (vin),
foreign key (business_tax_id, drivers_license_number) references Customer(business_tax_id, drivers_license_number),
FOREIGN KEY (inventoryClerk) REFERENCES InventoryClerk(username),
FOREIGN KEY (vin) REFERENCES Vehicle(vin)
);

CREATE TABLE Bought (
vin varchar(250),
sell_date datetime NOT NULL,
business_tax_id varchar(250) NOT NULL,
drivers_license_number varchar(250),
salespeople varchar(250),
PRIMARY KEY (vin),
foreign key (business_tax_id, drivers_license_number) references Customer(business_tax_id, drivers_license_number),
FOREIGN KEY (salespeople) REFERENCES Salespeople(username),
FOREIGN KEY (vin) REFERENCES Vehicle(vin)
);

CREATE TABLE Vendor (
name varchar(250),
state varchar(250) NOT NULL,
street varchar(250) NOT NULL,
city varchar(250) NOT NULL,
postal_code varchar(250) NOT NULL,
phone varchar(250) NOT NULL,
PRIMARY KEY (name)
);

CREATE TABLE PartsOrder (
vin varchar(250) NOT NULL,
purchase_order_number varchar(250),
PRIMARY KEY (purchase_order_number),
FOREIGN KEY (vin) REFERENCES Vehicle(vin)
);

CREATE TABLE Part (
part_number varchar(250),
purchase_order_number varchar(250),
part_quantity int(32) NOT NULL,
cost_per_part float(32)NOT NULL,
description varchar(250) NOT NULL,
vendor_name varchar(250) NOT NULL,
current_status ENUM('ordered', 'received', 'installed'),
PRIMARY KEY (part_number, purchase_order_number),
FOREIGN KEY (purchase_order_number) REFERENCES PartsOrder (purchase_order_number),
FOREIGN KEY (vendor_name) REFERENCES Vendor(name)
);

