drop database photosdb;
create database photosdb;

use photosdb; 

create table users (
	username varchar (50) primary key not null,
	password varchar (50) not null,
	avatar int null
);

create table photos (
	idphoto	varchar(36) not null primary key,
	username varchar (50) not null,
	autor varchar (50) not null,
	file varchar (50) unique,
	name varchar (50),
	description varchar (50),
	foreign key (username) references users(username),
	foreign key (autor) references users(username)
);

create table comments (
	idcomment int not null auto_increment primary key,
	username varchar (50) not null,
	idphoto varchar(36) not null,
	content varchar(500) not null,
	foreign key (username) references users(username) on delete cascade,
	foreign key (idphoto) references photos(idphoto) on delete cascade
);

create table categories(
	idcategory int not null auto_increment primary key,
	nombre varchar(50)	
);


create table relationphotocategory (
	primary key  (idphoto, idcategory),
	idphoto varchar(36) not null,
	idcategory int not null,
	foreign key (idphoto) references photos(idphoto) on delete cascade,
	foreign key (idcategory) references categories(idcategory) on delete cascade
);

create table albums (
	idalbum int not null auto_increment primary key,
	nombre varchar(50) not null,
	description varchar(50) not null,
	username varchar (50) not null,
	foreign key (username) references users(username) on delete cascade 
);

create table relationphotoalbum (
	primary key  (idphoto, idalbum),
	idphoto varchar(36) not null,
	idalbum int not null,
	foreign key (idphoto) references photos(idphoto) on delete cascade,
	foreign key (idalbum) references albums(idalbum) on delete cascade
);

create table relacionuserfollows (
	username varchar (50) not null,
	followed varchar (50) not null,
	primary key (followed, username),
	foreign key (followed) references users(username),
	foreign key  (username) references users(username)
);

create table relacionusercategories (
	username varchar (50) not null,
	idcategory int not null,
	foreign key (username) references users(username),
	foreign key (idcategory)  references categories(idcategory)
);
