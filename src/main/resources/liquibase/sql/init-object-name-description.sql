-- create table that will tire object's name and it's description
create table if not exists ThreeDMap.object_name_description (
	name varchar(50) Primary Key, 
	description varchar(100)
);
-- add description
comment on table ThreeDMap.sprite is 'This table is intendet to store descriptions of objects.';
