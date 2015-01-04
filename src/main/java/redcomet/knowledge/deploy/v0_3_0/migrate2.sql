-- パスワードリセット
drop table if exists PASSWORD_RESETS cascade;

create table PASSWORD_RESETS (
  ID character varying(256) not null
  , USER_KEY character varying(256)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PASSWORD_RESETS_PKC primary key (ID)
) ;

-- 仮登録ユーザ
drop table if exists PROVISIONAL_REGISTRATIONS cascade;

create table PROVISIONAL_REGISTRATIONS (
  ID character varying(256) not null
  , USER_KEY character varying(256) not null
  , USER_NAME character varying(256) not null
  , PASSWORD character varying(1024) not null
  , SALT character varying(1024) not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PROVISIONAL_REGISTRATIONS_PKC primary key (ID)
) ;

-- ハッシュ生成の設定
drop table if exists HASH_CONFIGS cascade;

create table HASH_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , HASH_ITERATIONS integer not null
  , HASH_SIZE_BITS integer not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint HASH_CONFIGS_PKC primary key (SYSTEM_NAME)
) ;

-- メール設定
drop table if exists MAIL_CONFIGS cascade;

create table MAIL_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , HOST character varying(256) not null
  , PORT integer not null
  , AUTH_TYPE integer not null
  , SMTP_ID character varying(256)
  , SMTP_PASSWORD character varying(1048)
  , SALT character varying(1048)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_CONFIGS_PKC primary key (SYSTEM_NAME)
) ;

-- コンフィグ
drop table if exists SYSTEM_CONFIGS cascade;

create table SYSTEM_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , CONFIG_NAME character varying(256) not null
  , CONFIG_VALUE character varying(1024)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SYSTEM_CONFIGS_PKC primary key (SYSTEM_NAME,CONFIG_NAME)
) ;

-- メール
drop table if exists MAILS cascade;

create table MAILS (
  MAIL_ID character varying(64) not null
  , STATUS integer not null
  , TO_ADDRESS character varying(256) not null
  , TO_NAME character varying(256)
  , FROM_ADDRESS character varying(256)
  , FROM_NAME character varying(256)
  , TITLE character varying(256) not null
  , CONTENT text
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAILS_PKC primary key (MAIL_ID)
) ;

create index IDX_MAILS_STATUS
  on MAILS(STATUS);



alter table HASH_CONFIGS
  add constraint HASH_CONFIGS_FK1 foreign key (SYSTEM_NAME) references SYSTEMS(SYSTEM_NAME)
  on delete cascade
  on update cascade;

alter table MAIL_CONFIGS
  add constraint MAIL_CONFIGS_FK1 foreign key (SYSTEM_NAME) references SYSTEMS(SYSTEM_NAME)
  on delete cascade
  on update cascade;

alter table SYSTEM_CONFIGS
  add constraint SYSTEM_CONFIGS_FK1 foreign key (SYSTEM_NAME) references SYSTEMS(SYSTEM_NAME)
  on delete cascade
  on update cascade;


comment on table PASSWORD_RESETS is 'パスワードリセット';
comment on column PASSWORD_RESETS.ID is 'パスワードリセットID';
comment on column PASSWORD_RESETS.USER_KEY is 'ユーザKEY';
comment on column PASSWORD_RESETS.ROW_ID is '行ID';
comment on column PASSWORD_RESETS.INSERT_USER is '登録ユーザ';
comment on column PASSWORD_RESETS.INSERT_DATETIME is '登録日時';
comment on column PASSWORD_RESETS.UPDATE_USER is '更新ユーザ';
comment on column PASSWORD_RESETS.UPDATE_DATETIME is '更新日時';
comment on column PASSWORD_RESETS.DELETE_FLAG is '削除フラグ';

comment on table PROVISIONAL_REGISTRATIONS is '仮登録ユーザ';
comment on column PROVISIONAL_REGISTRATIONS.ID is '仮発行ID';
comment on column PROVISIONAL_REGISTRATIONS.USER_KEY is 'ユーザKEY';
comment on column PROVISIONAL_REGISTRATIONS.USER_NAME is 'ユーザ名';
comment on column PROVISIONAL_REGISTRATIONS.PASSWORD is 'パスワード';
comment on column PROVISIONAL_REGISTRATIONS.SALT is 'SALT';
comment on column PROVISIONAL_REGISTRATIONS.ROW_ID is '行ID';
comment on column PROVISIONAL_REGISTRATIONS.INSERT_USER is '登録ユーザ';
comment on column PROVISIONAL_REGISTRATIONS.INSERT_DATETIME is '登録日時';
comment on column PROVISIONAL_REGISTRATIONS.UPDATE_USER is '更新ユーザ';
comment on column PROVISIONAL_REGISTRATIONS.UPDATE_DATETIME is '更新日時';
comment on column PROVISIONAL_REGISTRATIONS.DELETE_FLAG is '削除フラグ';

comment on table HASH_CONFIGS is 'ハッシュ生成の設定';
comment on column HASH_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column HASH_CONFIGS.HASH_ITERATIONS is 'HASH_ITERATIONS';
comment on column HASH_CONFIGS.HASH_SIZE_BITS is 'HASH_SIZE_BITS';
comment on column HASH_CONFIGS.ROW_ID is '行ID';
comment on column HASH_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column HASH_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column HASH_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column HASH_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column HASH_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_CONFIGS is 'メール設定';
comment on column MAIL_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column MAIL_CONFIGS.HOST is 'SMTP_HOST';
comment on column MAIL_CONFIGS.PORT is 'SMTP_PORT';
comment on column MAIL_CONFIGS.AUTH_TYPE is 'AUTH_TYPE';
comment on column MAIL_CONFIGS.SMTP_ID is 'SMTP_ID';
comment on column MAIL_CONFIGS.SMTP_PASSWORD is 'SMTP_PASSWORD	 暗号化（可逆）';
comment on column MAIL_CONFIGS.SALT is 'SALT';
comment on column MAIL_CONFIGS.ROW_ID is '行ID';
comment on column MAIL_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column MAIL_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column MAIL_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table SYSTEM_CONFIGS is 'コンフィグ';
comment on column SYSTEM_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column SYSTEM_CONFIGS.CONFIG_NAME is 'コンフィグ名';
comment on column SYSTEM_CONFIGS.CONFIG_VALUE is 'コンフィグ値';
comment on column SYSTEM_CONFIGS.ROW_ID is '行ID';
comment on column SYSTEM_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column SYSTEM_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column SYSTEM_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column SYSTEM_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column SYSTEM_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table MAILS is 'メール';
comment on column MAILS.MAIL_ID is 'MAIL_ID';
comment on column MAILS.STATUS is 'ステータス';
comment on column MAILS.TO_ADDRESS is '送信先';
comment on column MAILS.TO_NAME is '送信先名';
comment on column MAILS.FROM_ADDRESS is '送信元';
comment on column MAILS.FROM_NAME is '送信元名';
comment on column MAILS.TITLE is 'タイトル';
comment on column MAILS.CONTENT is 'メッセージ';
comment on column MAILS.ROW_ID is '行ID';
comment on column MAILS.INSERT_USER is '登録ユーザ';
comment on column MAILS.INSERT_DATETIME is '登録日時';
comment on column MAILS.UPDATE_USER is '更新ユーザ';
comment on column MAILS.UPDATE_DATETIME is '更新日時';
comment on column MAILS.DELETE_FLAG is '削除フラグ';


-- 既存のテーブルへの変更
ALTER TABLE USERS CHANGE COLUMN PASSWORD PASSWORD character varying(1024) not null;
ALTER TABLE USERS ADD COLUMN SALT character varying(1024);

comment on column USERS.PASSWORD is 'パスワード	 ハッシュ(不可逆)';
comment on column USERS.SALT is 'SALT';

