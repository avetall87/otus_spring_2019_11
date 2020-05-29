create table tokens (
    id bigserial constraint token_pk PRIMARY KEY,
    token_name varchar(4000) not null,
    creation_date timestamp default current_timestamp not null,
    sent_to_index boolean default false
);

create index tokens_send_to_index ON tokens (sent_to_index);

create table questions (
    id bigserial constraint question_pk PRIMARY KEY,
    author_full_name varchar(255) not null,
    title varchar(255) not null,
    url varchar(255),
    creation_date timestamp,
    update_date timestamp default current_date,
    token_id bigint references tokens (id) ON DELETE CASCADE,
    sent_to_index boolean default false
);

create index question_token_idx ON questions (token_id);
create index question_authorFullName_idx ON questions (author_full_name);
create index question_title_idx ON questions (title);
create index question_url_idx ON questions (url);
create index question_creation_date_idx ON questions (creation_date);
create index question_update_date_idx ON questions (sent_to_index);

create unique index question_unique_idx on questions (author_full_name, title, url, creation_date, token_id);