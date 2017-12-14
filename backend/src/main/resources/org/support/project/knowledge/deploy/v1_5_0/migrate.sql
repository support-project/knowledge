-- メールから投稿
drop table if exists MAIL_POSTS cascade;

create table MAIL_POSTS (
  MESSAGE_ID character varying(128) not null
  , POST_KIND integer not null
  , ID BIGINT not null
  , SENDER text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_POSTS_PKC primary key (MESSAGE_ID)
) ;

-- メールから投稿する条件
drop table if exists MAIL_HOOK_CONDITIONS cascade;

create table MAIL_HOOK_CONDITIONS (
  HOOK_ID INTEGER not null
  , CONDITION_NO integer not null
  , CONDITION_KIND integer not null
  , CONDITION character varying(256)
  , PROCESS_USER integer not null
  , PROCESS_USER_KIND integer not null
  , PUBLIC_FLAG integer not null
  , TAGS text
  , VIEWERS text
  , EDITORS text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOK_CONDITIONS_PKC primary key (HOOK_ID,CONDITION_NO)
) ;

-- 受信したメールからの処理
drop table if exists MAIL_HOOKS cascade;

create table MAIL_HOOKS (
  HOOK_ID SERIAL not null
  , MAIL_PROTOCOL character varying(10) not null
  , MAIL_HOST character varying(256) not null
  , MAIL_PORT integer not null
  , MAIL_USER character varying(256)
  , MAIL_PASS character varying(1024)
  , MAIL_PASS_SALT character varying(1024)
  , MAIL_FOLDER character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOKS_PKC primary key (HOOK_ID)
) ;

comment on table MAIL_POSTS is 'メールから投稿';
comment on column MAIL_POSTS.MESSAGE_ID is 'Message-ID';
comment on column MAIL_POSTS.POST_KIND is '投稿区分	 1: Knowledge 2:Comment';
comment on column MAIL_POSTS.ID is 'ID';
comment on column MAIL_POSTS.SENDER is 'SENDER';
comment on column MAIL_POSTS.INSERT_USER is '登録ユーザ';
comment on column MAIL_POSTS.INSERT_DATETIME is '登録日時';
comment on column MAIL_POSTS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_POSTS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_POSTS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOK_CONDITIONS is 'メールから投稿する条件';
comment on column MAIL_HOOK_CONDITIONS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOK_CONDITIONS.CONDITION_NO is 'CONDITION_NO';
comment on column MAIL_HOOK_CONDITIONS.CONDITION_KIND is '条件の種類	 1:宛先が「条件文字」であった場合';
comment on column MAIL_HOOK_CONDITIONS.CONDITION is '条件の文字';
comment on column MAIL_HOOK_CONDITIONS.PROCESS_USER is '投稿者';
comment on column MAIL_HOOK_CONDITIONS.PROCESS_USER_KIND is '投稿者の指定	 1:送信者のメールアドレスから、2:常に固定';
comment on column MAIL_HOOK_CONDITIONS.PUBLIC_FLAG is '公開区分';
comment on column MAIL_HOOK_CONDITIONS.TAGS is 'タグ';
comment on column MAIL_HOOK_CONDITIONS.VIEWERS is '公開先';
comment on column MAIL_HOOK_CONDITIONS.EDITORS is '共同編集者';
comment on column MAIL_HOOK_CONDITIONS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOK_CONDITIONS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOK_CONDITIONS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOK_CONDITIONS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOK_CONDITIONS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOKS is '受信したメールからの処理';
comment on column MAIL_HOOKS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOKS.MAIL_PROTOCOL is 'MAIL_PROTOCOL';
comment on column MAIL_HOOKS.MAIL_HOST is 'MAIL_HOST';
comment on column MAIL_HOOKS.MAIL_PORT is 'MAIL_PORT';
comment on column MAIL_HOOKS.MAIL_USER is 'MAIL_USER';
comment on column MAIL_HOOKS.MAIL_PASS is 'MAIL_PASS';
comment on column MAIL_HOOKS.MAIL_PASS_SALT is 'MAIL_PASS_SALT';
comment on column MAIL_HOOKS.MAIL_FOLDER is 'MAIL_FOLDER';
comment on column MAIL_HOOKS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOKS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOKS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOKS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOKS.DELETE_FLAG is '削除フラグ';
