-- システム付加情報
drop table if exists SYSTEM_ATTRIBUTES cascade;

create table SYSTEM_ATTRIBUTES (
  SYSTEM_NAME character varying(64) not null
  , CONFIG_NAME character varying(256) not null
  , CONFIG_VALUE text
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SYSTEM_ATTRIBUTES_PKC primary key (SYSTEM_NAME,CONFIG_NAME)
) ;

comment on table SYSTEM_ATTRIBUTES is 'システム付加情報';
comment on column SYSTEM_ATTRIBUTES.SYSTEM_NAME is 'システム名';
comment on column SYSTEM_ATTRIBUTES.CONFIG_NAME is 'コンフィグ名';
comment on column SYSTEM_ATTRIBUTES.CONFIG_VALUE is 'コンフィグ値';
comment on column SYSTEM_ATTRIBUTES.ROW_ID is '行ID';
comment on column SYSTEM_ATTRIBUTES.INSERT_USER is '登録ユーザ';
comment on column SYSTEM_ATTRIBUTES.INSERT_DATETIME is '登録日時';
comment on column SYSTEM_ATTRIBUTES.UPDATE_USER is '更新ユーザ';
comment on column SYSTEM_ATTRIBUTES.UPDATE_DATETIME is '更新日時';
comment on column SYSTEM_ATTRIBUTES.DELETE_FLAG is '削除フラグ';


