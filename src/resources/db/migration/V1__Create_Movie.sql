

create table movie (
    id bigint auto_increment,
    name varchar(255),
    genre varchar(255),
    yearReleased INTEGER,
    rating  REAL
);

insert into movie(name, genre, yearReleased, rating) values('The Matrix', 'Action,Sci-fi', 1999, 4.3);
insert into movie(name, genre, yearReleased, rating) values('From Russia with Love', 'Action', 1963, 4.2);
insert into movie(name, genre, yearReleased, rating) values('Star Wars', 'Action,Sci-fi', 2017, null);


