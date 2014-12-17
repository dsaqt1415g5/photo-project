drop database photosdb;
create database photosdb;

use photosdb; 

create table Users (
	username varchar (50) primary key not null,
	password varchar (50) not null,
	avatar int null
);

create table Categories(
	idcategory int not null auto_increment primary key,
	nombre varchar(50)	
);

create table Photos (
	idphoto	int not null auto_increment primary key,
	username varchar (50) not null,
	idautor	varchar (50) not null,
	file varchar (50) unique,
	name varchar (50),
	description varchar (50),
	creation timestamp,
	foreign key (username) references Users(username),
	foreign key (idautor) references Users(username)
);

create table RelationPhotoCategory (
	primary key  (idphoto, idcategory),
	idphoto int not null,
	idcategory int not null,
	foreign key (idphoto) references Photos(idphoto) on delete cascade,
	foreign key (idcategory) references Categories(idcategory) on delete cascade
);

create table Comments (
	idcomment int not null auto_increment primary key,
	username varchar (50) not null,
	idphoto int not null,
	creation timestamp not null,
	content varchar(500) not null,
	foreign key (username) references Users(username) on delete cascade,
	foreign key (idphoto) references Photos(idphoto) on delete cascade
);

create table Albums (
	idalbum int not null auto_increment primary key,
	nombre varchar(50) not null,
	descritpion varchar(50) not null,
	username varchar (50) not null,
	foreign key (username) references Users(username) on delete cascade
);

create table RelationPhotoAlbum (
	primary key  (idphoto, idalbum),
	idphoto int not null,
	idalbum int not null,
	foreign key (idphoto) references Photos(idphoto) on delete cascade,
	foreign key (idalbum) references Albums(idalbum) on delete cascade
);




create table RelacionUserFollows (
	username varchar (50) not null,
	idfollowed varchar (50) not null,
	primary key (idfollowed, username),
	foreign key (idfollowed) references Users(username),
	foreign key  (username) references Users(username)
);

create table RelacionUserCategories (
	username varchar (50) not null,
	idcategory int not null,
	foreign key (username) references Users(username),
	foreign key (idcategory)  references Categories(idcategory)
);

