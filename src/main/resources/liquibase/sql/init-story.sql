-- create table that will store sprite objects
create table if not exists ThreeDMap.story (
	name varchar(50) Primary Key, 
	bytes bytea
);
-- add description
comment on table ThreeDMap.sprite is 'This table is intendet to store stories about landmarks.';