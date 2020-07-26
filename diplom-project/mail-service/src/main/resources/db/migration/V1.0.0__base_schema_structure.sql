create table mail_lists (
    id bigserial constraint mail_list_pk PRIMARY KEY,
    email varchar(1024) not null unique
);

create unique index mail_list_email on mail_lists(email);

create table mail_lists_logs (
  id bigserial constraint mail_list_log_pk PRIMARY KEY,
  mail_list_id bigint not null references mail_lists (id) ON DELETE CASCADE,
  url varchar(4000) not null,
  transfer_date timestamp not null,
  constraint mail_list_logs unique (mail_list_id, url)

);

create unique index mail_list_log_uniq on mail_lists_logs(mail_list_id, url);
create index mail_list_id_fk on mail_lists_logs(mail_list_id);
create index mail_list_transfer_date on mail_lists_logs(transfer_date);
create index mail_list_transfer_date_trunc on mail_lists_logs(date_trunc('day',transfer_date));