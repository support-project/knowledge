-- メール受信設定
drop table if exists MAIL_PROPERTIES cascade;

create table MAIL_PROPERTIES (
  HOOK_ID INTEGER not null
  , PROPERTY_KEY character varying(128) not null
  , PROPERTY_VALUE character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_PROPERTIES_PKC primary key (HOOK_ID,PROPERTY_KEY)
) ;

comment on table MAIL_PROPERTIES is 'メール受信設定';
comment on column MAIL_PROPERTIES.HOOK_ID is 'HOOK_ID';
comment on column MAIL_PROPERTIES.PROPERTY_KEY is 'PROPERTY_KEY';
comment on column MAIL_PROPERTIES.PROPERTY_VALUE is 'PROPERTY_VALUE';
comment on column MAIL_PROPERTIES.INSERT_USER is '登録ユーザ';
comment on column MAIL_PROPERTIES.INSERT_DATETIME is '登録日時';
comment on column MAIL_PROPERTIES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_PROPERTIES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_PROPERTIES.DELETE_FLAG is '削除フラグ';

