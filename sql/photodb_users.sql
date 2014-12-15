drop user 'photos'@'localhost';
create user 'photos'@'localhost' identified by 'photos';
grant all privileges on photosdb.* to 'photos'@'localhost';
flush privileges;