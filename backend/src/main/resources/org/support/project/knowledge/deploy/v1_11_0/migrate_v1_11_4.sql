-- いいねの通知状態
drop table if exists NOTIFICATION_STATUS cascade;

create table NOTIFICATION_STATUS (
  TYPE integer not null
  , TARGET_ID bigint not null
  , USER_ID integer not null
  , STATUS integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTIFICATION_STATUS_PKC primary key (TYPE,TARGET_ID,USER_ID)
) ;

-- メールから投稿の際の除外条件
drop table if exists MAIL_HOOK_IGNORE_CONDITIONS cascade;

create table MAIL_HOOK_IGNORE_CONDITIONS (
  HOOK_ID INTEGER not null
  , CONDITION_NO integer not null
  , IGNORE_CONDITION_NO integer not null
  , CONDITION_KIND integer not null
  , CONDITION character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOK_IGNORE_CONDITIONS_PKC primary key (HOOK_ID,CONDITION_NO,IGNORE_CONDITION_NO)
) ;

comment on table NOTIFICATION_STATUS is 'いいねの通知状態';
comment on column NOTIFICATION_STATUS.TYPE is '種類';
comment on column NOTIFICATION_STATUS.TARGET_ID is 'ターゲットのID';
comment on column NOTIFICATION_STATUS.USER_ID is '登録者';
comment on column NOTIFICATION_STATUS.STATUS is '通知の状態';
comment on column NOTIFICATION_STATUS.INSERT_USER is '登録ユーザ';
comment on column NOTIFICATION_STATUS.INSERT_DATETIME is '登録日時';
comment on column NOTIFICATION_STATUS.UPDATE_USER is '更新ユーザ';
comment on column NOTIFICATION_STATUS.UPDATE_DATETIME is '更新日時';
comment on column NOTIFICATION_STATUS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOK_IGNORE_CONDITIONS is 'メールから投稿の際の除外条件';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION_NO is 'CONDITION_NO';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.IGNORE_CONDITION_NO is 'IGNORE_CONDITION_NO';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION_KIND is '条件の種類   1:宛先が「条件文字」であった場合';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION is '条件の文字';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.DELETE_FLAG is '削除フラグ';

