create user player identified by 'playerPass';

create table highscores
(
	player_name varchar(16) not null
		primary key,
	time int not null,
	map_name enum('DESERT', 'URBAN') not null
);

grant alter, create, delete, create view, drop, index, insert, references, select, show view, trigger, update on table highscores to player;

