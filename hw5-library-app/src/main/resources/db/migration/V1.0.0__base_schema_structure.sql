create table authors (
 id long auto_increment constraint author_pk PRIMARY KEY,
 first_name varchar2(250) not null,
 last_name varchar2(250) not null,
 patronymic varchar2(250) not null
);

create table books (
  id long auto_increment constraint book_pk PRIMARY KEY,
  name varchar2(1024) not null,
  description varchar2 (4000 char)
);

create table authors_books (
    id long auto_increment constraint author_book_pk PRIMARY KEY,
    author_id long not null references authors(id) ON DELETE CASCADE,
    book_id long not null references books(id) ON DELETE CASCADE

);

create index authors_books_author_id_idx ON authors_books(author_id);
create index authors_books_book_id_idx ON authors_books(book_id);

create table genres (
  id long auto_increment constraint genre_pk PRIMARY KEY,
  name varchar2(1024) not null
);

create table books_genres (
    id long auto_increment constraint book_genre_pk PRIMARY KEY,
    book_id long not null references books(id) ON DELETE CASCADE,
    genre_id long not null references genres(id) ON DELETE CASCADE
);

create index books_genres_book_id ON books_genres(book_id);
create index books_genres_genre_id ON books_genres(genre_id);
