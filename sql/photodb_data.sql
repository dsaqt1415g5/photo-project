insert into Users (username, password, avatar) values ('pau', 'pau', null);
insert into Users (username, password, avatar) values ('cris', 'cris', null);
insert into Users (username, password, avatar) values ('oalex', 'alex', null);

insert into Photos (iduser, idautor, name, description) values (1, 1, 'foto1', 'holahola');
insert into Photos (iduser, idautor, name, description) values (2, 2, 'foto2', 'holahola');
insert into Photos (iduser, idautor, name, description) values (3, 3, 'foto3', 'holahola');

update Users set avatar = 1 where userid = 1;
update Users set avatar = 2 where userid = 2;
update Users set avatar = 3 where userid = 3;


insert into Comments (iduser, idphoto, content) values (1,1,'comentario de pau a la foto de pau');
insert into Comments (iduser, idphoto, content) values (1,2,'comentario de pau a la 2a foto de pau');
insert into Comments (iduser, idphoto, content) values (2,3,'comentario de cris a la 3a foto de pau');


insert into Categories (nombre) values ('aviones');
insert into Categories (nombre) values ('bmx');
insert into Categories (nombre) values ('deportes');

insert into RelationPhotoCategory (idphoto, idcategory) values (1,1);
insert into RelationPhotoCategory (idphoto, idcategory) values (2,2);
insert into RelationPhotoCategory (idphoto, idcategory) values (2,3);
insert into RelationPhotoCategory (idphoto, idcategory) values (3,2);
insert into RelationPhotoCategory (idphoto, idcategory) values (3,3);

insert into Albums (nombre, description, iduser) values ('Bmx','bmx voladoras y deportes', 1);

insert into RelationPhotoAlbum (idphoto, idalbum) values (1,1);
insert into RelationPhotoAlbum (idphoto, idalbum) values (2,1);
insert into RelationPhotoAlbum (idphoto, idalbum) values (3,1);


insert into RelacionUserFollows (iduser, idfollowed) values (2, 1);
insert into RelacionUserFollows (iduser, idfollowed) values (3, 1);

insert into RelacionUserCategories (iduser, idcategory) values (1, 1);
insert into RelacionUserCategories (iduser, idcategory) values (1, 2);
insert into RelacionUserCategories (iduser, idcategory) values (1, 3);