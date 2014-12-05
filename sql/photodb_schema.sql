drop database photosdb;
create database photosdb;

use photosdb;

create table Users (
	iduser	int not null auto_increment primary key,
	username varchar (50) unique not null,
	password varchar (50) not null,
	avatar int null,
	idprofile int not null
	);

create table Categories(
	idcategory primary key int not null auto_increment,
	nombre varchar(50)	
);

create table Photos (
	idphoto	int not null auto_increment primary key,
	iduser  int not null,
	idautor	int not null,
	file varchar (50) unique,
	name varchar (50) not null,
	description varchar (50) not null,
	creation timestamp,
	foreign key (iduser) references Users(iduser),
	foreign key (idautor) references Users(iduser)
);

create table RelationPhotoCategory (
	primary key  (idphoto, idcategory),
	idphoto int not null,
	idcategory int not null,
	foreign key (idphoto) references Photos(idphoto) on delete cascade,
	foreign key (idcategory) references Categories(idcategory) on delete cascade
);

alter table Users add foreign key (avatar) references Photos(idphoto);
insert into Users (username, password, avatar) values ('PauNoSuspendeDsa', 'holahola', null);
insert into Photos (iduser, idautor, name, description) values (1, 1, 'dfgh', 'sdghs');
update Users set avatar = 1 where iduser = 1;

create table Comments (
	idcomment int not null auto_increment primary key,
	iduser int not null,
	idphoto int not null,
	creation timestamp not null,
	content varchar(500) not null,
	foreign key (iduser) references Users(iduser) on delete cascade,
	foreign key (idphoto) references Photos(idphoto) on delete cascade
);

create table Albums (
	idalbum int not null auto increment primary key,
	nombre varchar(50) not null,
	descritpion varchar(50) not null,
	iduser int not null,
	foreign key (iduser) references Users(iduser) on delete cascade
);

create table RelationPhotoAlbum (
	primary key  (idphoto, idalbum),
	idphoto int not null,
	idalbum int not null,
	foreign key (idphoto) references Photos(idphoto) on delete cascade,
	foreign key (idalbum) references Albums(idalbum) on delete cascade
);




create table RelacionUserFollows (
	iduser int not null,
	idfollowed int not null,
	primary key (idfollowed, iduser),
	foreign key (idfollowed) references Users(iduser),
	foreign key  (iduser) references Users(iduser)
	
);

