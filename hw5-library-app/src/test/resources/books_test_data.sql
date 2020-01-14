delete from authors_books;
delete from books_genres;

delete from authors;
delete from genres;
delete from books;

insert into authors (id, first_name, last_name, patronymic)
values(101,'1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values(110,'1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values(115,'1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values(120,'1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values(130,'1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values(140,'1', '2', '3');

insert into genres (id, name) values (101, 'test');

insert into genres (id, name) values (100, 'test');

insert into genres (id, name) values (110, 'test');

insert into genres (id, name) values (115, 'test');

insert into genres (id, name) values (120, 'test');

insert into books (id, name, description) values(100, 'name', 'descr');

insert into books (id, name, description) values(101, 'name', 'descr');

insert into books (name, description) values('name_book', 'description');

insert into authors_books (author_id, book_id) values (110, 100);

insert into authors_books (author_id, book_id) values (115, 101);

insert into books_genres (book_id, genre_id) values (100, 100);

insert into books_genres (book_id, genre_id) values (101, 115);