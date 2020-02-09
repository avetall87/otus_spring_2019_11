insert into authors (first_name, last_name, patronymic)
values ('1', '2', '3');
insert into authors (id, first_name, last_name, patronymic)
values (100, '1', '2', '3');
insert into authors (id, first_name, last_name, patronymic)
values (110, '1', '2', '3');
insert into authors (id, first_name, last_name, patronymic)
values (115, '115', '115', '115');

insert into genres (id, name)
values (100, 'test');
insert into genres (id, name)
values (101, 'test');
insert into genres (id, name)
values (110, 'test');
insert into genres (id, name)
values (115, 'test');
insert into genres (id, name)
values (120, 'test');

insert into books (id, name, description)
values (100, 'name_1', 'descr_1');
insert into books (id, name, description)
values (101, 'name_2', 'descr_2');
insert into books (name, description)
values ('name_book', 'description');

insert into authors_books (author_id, book_id)
values (100, 100);
insert into authors_books (author_id, book_id)
values (100, 101);
insert into authors_books (author_id, book_id)
values (110, 100);
insert into authors_books (author_id, book_id)
values (115, 101);
insert into books_genres (book_id, genre_id)
values (100, 100);
insert into books_genres (book_id, genre_id)
values (101, 115);

insert into comments (book_id, comment)
values (100, 'comment1');
insert into comments (book_id, comment)
values (100, 'comment2');
insert into comments (book_id, comment)
values (100, 'comment3');