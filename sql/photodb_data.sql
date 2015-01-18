insert into users (username, password, avatar) values ('pau', 'pau', null);
insert into users (username, password, avatar) values ('cris', 'cris', null);
insert into users (username, password, avatar) values ('alex', 'alex', null);

-- insert into Photos (idphoto, username, autor, name, description) values ('1','pau','pau', 'Barcelona', 'holahola');
-- insert into Photos (idphoto, username, autor, name, description) values ('2','cris','alex', 'Castelldefels', 'holahola');
-- insert into Photos (idphoto, username, autor, name, description) values ('3','alex','cris', 'Honolulu', 'holahola');

update users set avatar = 1 where username = 'pau';
update users set avatar = 2 where username = 'cris';
update users set avatar = 3 where username = 'alex';


-- insert into Comments (username, idphoto, content) values ('pau','1','comentario de pau a la 1a foto de pau');
-- insert into Comments (username, idphoto, content) values ('cris','1','comentario de cris a la 1a foto de pau');
-- insert into Comments (username, idphoto, content) values ('alex','3','comentario de alex a la 3a foto de pau');


insert into categories (nombre) values ('futbol');
insert into categories (nombre) values ('moda');
insert into categories (nombre) values ('coches');

-- insert into RelationPhotoCategory (idphoto, idcategory) values ('1',1);
-- insert into RelationPhotoCategory (idphoto, idcategory) values ('2',2);
-- insert into RelationPhotoCategory (idphoto, idcategory) values ('2',3);
-- insert into RelationPhotoCategory (idphoto, idcategory) values ('3',2);
-- insert into RelationPhotoCategory (idphoto, idcategory) values ('3',3);

insert into albums (nombre, description, username) values ('coches','bmx voladoras y deportes', 'pau');

-- insert into RelationPhotoAlbum (idphoto, idalbum) values ('1',1);
-- insert into RelationPhotoAlbum (idphoto, idalbum) values ('2',1);
-- insert into RelationPhotoAlbum (idphoto, idalbum) values ('3',1);


insert into relacionuserfollows (username, followed) values ('pau', 'cris');
insert into relacionuserfollows (username, followed) values ('alex', 'cris');

insert into relacionusercategories (username, idcategory) values ('pau', 1);
insert into relacionusercategories (username, idcategory) values ('pau', 2);
insert into relacionusercategories (username, idcategory) values ('pau', 3);



