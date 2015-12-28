-- Webhooks
drop table if exists WEBHOOKS cascade;

create table WEBHOOKS (
  WEBHOOK_ID character varying(64) not null
  , STATUS integer not null
  , HOOK character varying(20)
  , CONTENT text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint WEBHOOKS_PKC primary key (WEBHOOK_ID)
) ;

create index IDX_WEBHOOKS_STATUS
  on WEBHOOKS(STATUS);

comment on table WEBHOOKS is 'Webhooks';
comment on column WEBHOOKS.WEBHOOK_ID is 'WEBHOOK ID';
comment on column WEBHOOKS.STATUS is 'ステータス';
comment on column WEBHOOKS.HOOK is 'HOOK';
comment on column WEBHOOKS.CONTENT is '通知用json文字列';
comment on column WEBHOOKS.INSERT_USER is '登録ユーザ';
comment on column WEBHOOKS.INSERT_DATETIME is '登録日時';
comment on column WEBHOOKS.UPDATE_USER is '更新ユーザ';
comment on column WEBHOOKS.UPDATE_DATETIME is '更新日時';
comment on column WEBHOOKS.DELETE_FLAG is '削除フラグ';