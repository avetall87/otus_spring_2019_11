delete
from authors;
delete
from books;

insert into authors (first_name, last_name, patronymic)
values ('1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values (100, '1', '2', '3');

insert into authors (id, first_name, last_name, patronymic)
values (101, '1', '2', '3');

insert into books (id, name, description)
values (100, 'name', 'descr');

insert into authors_books (author_id, book_id)
values (100, 100);