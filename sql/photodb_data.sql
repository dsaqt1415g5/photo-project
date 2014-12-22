insert into Users (username, password, avatar) values ('pau', 'pau', null);
insert into Users (username, password, avatar) values ('cris', 'cris', null);
insert into Users (username, password, avatar) values ('alex', 'alex', null);

insert into Photos (username, autor, name, description) values ('pau','pau', 'Barcelona', 'holahola');
insert into Photos (username, autor, name, description) values ('cris','alex', 'Castelldefels', 'holahola');
insert into Photos (username, autor, name, description) values ('alex','cris', 'Honolulu', 'holahola');

update Users set avatar = 1 where username = 'pau';
update Users set avatar = 2 where username = 'cris';
update Users set avatar = 3 where username = 'alex';


insert into Comments (username, idphoto, content) values ('pau',1,'comentario de pau a la foto de pau');
insert into Comments (username, idphoto, content) values ('cris',2,'comentario de pau a la 2a foto de pau');
insert into Comments (username, idphoto, content) values ('alex',3,'comentario de cris a la 3a foto de pau');


insert into Categories (nombre) values ('futbol');
insert into Categories (nombre) values ('moda');
insert into Categories (nombre) values ('coches');

insert into RelationPhotoCategory (idphoto, idcategory) values (1,1);
insert into RelationPhotoCategory (idphoto, idcategory) values (2,2);
insert into RelationPhotoCategory (idphoto, idcategory) values (2,3);
insert into RelationPhotoCategory (idphoto, idcategory) values (3,2);
insert into RelationPhotoCategory (idphoto, idcategory) values (3,3);

insert into Albums (nombre, description, username) values ('coches','bmx voladoras y deportes', 'pau');

insert into RelationPhotoAlbum (idphoto, idalbum) values (1,1);
insert into RelationPhotoAlbum (idphoto, idalbum) values (2,1);
insert into RelationPhotoAlbum (idphoto, idalbum) values (3,1);


insert into RelacionUserFollows (username, followed) values ('pau', 'cris');
insert into RelacionUserFollows (username, followed) values ('alex', 'cris');

insert into RelacionUserCategories (username, idcategory) values ('pau', 1);
insert into RelacionUserCategories (username, idcategory) values ('pau', 2);
insert into RelacionUserCategories (username, idcategory) values ('pau', 3);