create table hel_hello
(
  id         int auto_increment
    primary key,
  name       char(64)                            not null,
  gmt_create datetime                            not null,
  gmt_modify timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
  version    bigint(19)                          not null,
  is_deleted int                                 not null
);

INSERT INTO hello.hel_hello (id, `name`, gmt_create, gmt_modify, version, is_deleted) VALUES (1, 'hello world', '2019-02-15 04:09:54', '2019-02-15 04:09:57', 0, 0);