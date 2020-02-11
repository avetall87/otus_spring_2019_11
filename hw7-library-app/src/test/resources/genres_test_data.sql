delete from authors;
delete from genres;
delete from books;

insert into books (id, name, description) values (100, 'test', 'description');

insert into genres (id, name) values (100, 'test');
insert into genres (id, name) values (101, 'test');
insert into genres (id, name) values (110, 'test');
insert into genres (id, name) values (115, 'test');
insert into genres (id, name) values (120, 'test');

insert into books_genres (book_id, genre_id) values(100, 100);