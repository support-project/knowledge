-- 認証トークン
drop table if exists TOKENS cascade;

create table TOKENS (
  TOKEN character varying(128) not null
  , USER_ID integer not null
  , EXPIRES timestamp not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TOKENS_PKC primary key (TOKEN)
) ;

create unique index TOKENS_IX1
  on TOKENS(USER_ID);

comment on table TOKENS is '認証トークン';
comment on column TOKENS.TOKEN is 'TOKEN';
comment on column TOKENS.USER_ID is 'ユーザID';
comment on column TOKENS.EXPIRES is '有効期限';
comment on column TOKENS.INSERT_USER is '登録ユーザ';
comment on column TOKENS.INSERT_DATETIME is '登録日時';
comment on column TOKENS.UPDATE_USER is '更新ユーザ';
comment on column TOKENS.UPDATE_DATETIME is '更新日時';
comment on column TOKENS.DELETE_FLAG is '削除フラグ';

