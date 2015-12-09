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

