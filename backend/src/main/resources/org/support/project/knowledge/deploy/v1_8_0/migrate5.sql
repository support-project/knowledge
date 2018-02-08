-- ロケール毎のメールテンプレート
drop table if exists MAIL_LOCALE_TEMPLATES cascade;

create table MAIL_LOCALE_TEMPLATES (
  TEMPLATE_ID character varying(32) not null
  , KEY character varying(12) not null
  , TITLE text not null
  , CONTENT text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_LOCALE_TEMPLATES_PKC primary key (TEMPLATE_ID,KEY)
) ;

-- メールテンプレート
drop table if exists MAIL_TEMPLATES cascade;

create table MAIL_TEMPLATES (
  TEMPLATE_ID character varying(32) not null
  , TEMPLATE_TITLE character varying(128) not null
  , DESCRIPTION text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_TEMPLATES_PKC primary key (TEMPLATE_ID)
) ;

comment on table MAIL_LOCALE_TEMPLATES is 'ロケール毎のメールテンプレート';
comment on column MAIL_LOCALE_TEMPLATES.TEMPLATE_ID is 'テンプレートID';
comment on column MAIL_LOCALE_TEMPLATES.KEY is 'キー';
comment on column MAIL_LOCALE_TEMPLATES.TITLE is 'タイトル';
comment on column MAIL_LOCALE_TEMPLATES.CONTENT is '本文';
comment on column MAIL_LOCALE_TEMPLATES.INSERT_USER is '登録ユーザ';
comment on column MAIL_LOCALE_TEMPLATES.INSERT_DATETIME is '登録日時';
comment on column MAIL_LOCALE_TEMPLATES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_LOCALE_TEMPLATES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_LOCALE_TEMPLATES.DELETE_FLAG is '削除フラグ';

comment on table MAIL_TEMPLATES is 'メールテンプレート';
comment on column MAIL_TEMPLATES.TEMPLATE_ID is 'テンプレートID';
comment on column MAIL_TEMPLATES.TEMPLATE_TITLE is 'テンプレートタイトル';
comment on column MAIL_TEMPLATES.DESCRIPTION is '説明文';
comment on column MAIL_TEMPLATES.INSERT_USER is '登録ユーザ';
comment on column MAIL_TEMPLATES.INSERT_DATETIME is '登録日時';
comment on column MAIL_TEMPLATES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_TEMPLATES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_TEMPLATES.DELETE_FLAG is '削除フラグ';

