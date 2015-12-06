-- Webhook 設定
drop table if exists WEBHOOK_CONFIGS cascade;

create table WEBHOOK_CONFIGS (
  HOOK_ID serial not null
  , HOOK character varying(20) not null
  , URL character varying(256) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint WEBHOOK_CONFIGS_PKC primary key (HOOK_ID)
) ;

comment on table WEBHOOK_CONFIGS is 'Webhooks 設定';
comment on column WEBHOOK_CONFIGS.HOOK_ID is 'HOOK ID';
comment on column WEBHOOK_CONFIGS.HOOK is 'HOOK';
comment on column WEBHOOK_CONFIGS.URL is 'URL';
comment on column WEBHOOK_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column WEBHOOK_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column WEBHOOK_CONFIGS.DELETE_FLAG is '削除フラグ';