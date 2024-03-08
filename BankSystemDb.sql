CREATE TABLE `Users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_Name` varchar(45) DEFAULT NULL,
  `avg_Name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `date_Born` varchar(45) DEFAULT NULL,
  `number_Account` varchar(45) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create table users_roles (
user_id bigint not null,
role_id bigint not null,
primary key(user_id, role_id),
foreign key(user_id) references Users(id),
foreign key(role_id) references roles(id)
);

CREATE TABLE `Phones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` varchar(45) DEFAULT NULL,
  `user_Id` bigint DEFAULT NULL REFERENCES Users(id),
  PRIMARY KEY (`id`)
);

CREATE TABLE `Emails` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `user_Id` bigint DEFAULT NULL REFERENCES Users(id),
  PRIMARY KEY (`id`)
);