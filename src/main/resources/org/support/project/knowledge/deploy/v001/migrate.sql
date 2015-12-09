-- ユーザ設定
drop table if exists USER_CONFIGS cascade;

create table USER_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , USER_ID INTEGER not null
  , CONFIG_NAME character varying(256) not null
  , CONFIG_VALUE character varying(1024)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_CONFIGS_PKC primary key (SYSTEM_NAME,USER_ID,CONFIG_NAME)
) ;

comment on table USER_CONFIGS is 'ユーザ設定';
comment on column USER_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column USER_CONFIGS.USER_ID is 'ユーザID';
comment on column USER_CONFIGS.CONFIG_NAME is 'コンフィグ名';
comment on column USER_CONFIGS.CONFIG_VALUE is 'コンフィグ値';
comment on column USER_CONFIGS.ROW_ID is '行ID';
comment on column USER_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column USER_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column USER_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column USER_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column USER_CONFIGS.DELETE_FLAG is '削除フラグ';

