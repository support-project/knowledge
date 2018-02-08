-- CSRF_TOKENS
drop table if exists CSRF_TOKENS cascade;

create table CSRF_TOKENS (
  USER_ID integer not null
  , PROCESS_NAME character varying(64) not null
  , TOKEN character varying(128) not null
  , EXPIRES timestamp not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint CSRF_TOKENS_PKC primary key (USER_ID,PROCESS_NAME)
) ;

comment on table CSRF_TOKENS is 'CSRF_TOKENS';
comment on column CSRF_TOKENS.USER_ID is 'ユーザID';
comment on column CSRF_TOKENS.PROCESS_NAME is '処理名';
comment on column CSRF_TOKENS.TOKEN is 'TOKEN';
comment on column CSRF_TOKENS.EXPIRES is '有効期限';
comment on column CSRF_TOKENS.ROW_ID is '行ID';
comment on column CSRF_TOKENS.INSERT_USER is '登録ユーザ';
comment on column CSRF_TOKENS.INSERT_DATETIME is '登録日時';
comment on column CSRF_TOKENS.UPDATE_USER is '更新ユーザ';
comment on column CSRF_TOKENS.UPDATE_DATETIME is '更新日時';
comment on column CSRF_TOKENS.DELETE_FLAG is '削除フラグ';

-- Modify Tokens table
ALTER TABLE TOKENS DROP COLUMN IF EXISTS TOKEN_NAME;
ALTER TABLE TOKENS ADD COLUMN  TOKEN_NAME character varying(64);
ALTER TABLE TOKENS DROP COLUMN IF EXISTS DESCRIPTION;
ALTER TABLE TOKENS ADD COLUMN DESCRIPTION character varying(256);

comment on column TOKENS.TOKEN_TYPE is '登録種類	 1:公開API用、2:OAuthで発行したToken';
comment on column TOKENS.TOKEN_NAME is 'TOKEN名';
comment on column TOKENS.DESCRIPTION is 'メモ';

