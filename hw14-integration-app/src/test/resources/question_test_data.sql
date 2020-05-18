insert into tokens (id,token_name)
values (1,'test');

insert into tokens (id,token_name)
values (2,'test2');

insert into questions (author_full_name,
                       title,
                       url,
                       creation_date,
                       update_date,
                       token_id)
values ('test_name', 'title', 'url', null, null, 1);


insert into questions (author_full_name,
                       title,
                       url,
                       creation_date,
                       update_date,
                       token_id)
values ('test_name', 'title', 'url', null, null, 2);