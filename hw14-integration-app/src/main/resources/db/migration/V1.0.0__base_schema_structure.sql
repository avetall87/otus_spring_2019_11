create table tokens (
    id long auto_increment constraint token_pk PRIMARY KEY,
    token_name varchar2(4000) not null
);

create table questions (
    id long auto_increment constraint question_pk PRIMARY KEY,
    author_full_name varchar2(255) not null,
    title varchar2(255) not null,
    url varchar2(255),
    creation_date timestamp,
    update_date timestamp default current_timestamp(),
    token_id long references tokens (id) ON DELETE CASCADE
);

create index question_token_idx ON questions (token_id);
create index question_authorFullName_idx ON questions (author_full_name);
create index question_title_idx ON questions (title);
create index question_url_idx ON questions (url);
create index question_creation_date_idx ON questions (creation_date);
create index question_update_date_idx ON questions (update_date);

create unique index question_unique_idx on questions (author_full_name, title, url, creation_date, token_id);