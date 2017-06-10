-- ユーザのエイリアス
drop table if exists USER_ALIAS cascade;

create table USER_ALIAS (
  USER_ID INTEGER not null
  , AUTH_KEY character varying(64) not null
  , ALIAS_KEY character varying(256) not null
  , ALIAS_NAME character varying(256) not null
  , ALIAS_MAIL character varying(256)
  , USER_INFO_UPDATE integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_ALIAS_PKC primary key (USER_ID,AUTH_KEY)
) ;

create unique index USER_ALIAS_IX1
  on USER_ALIAS(AUTH_KEY,ALIAS_KEY);

comment on table USER_ALIAS is 'ユーザのエイリアス';
comment on column USER_ALIAS.USER_ID is 'ユーザID';
comment on column USER_ALIAS.AUTH_KEY is '認証設定キー';
comment on column USER_ALIAS.ALIAS_KEY is 'エイリアスのキー';
comment on column USER_ALIAS.ALIAS_NAME is 'エイリアスの表示名';
comment on column USER_ALIAS.ALIAS_MAIL is 'メールアドレス';
comment on column USER_ALIAS.USER_INFO_UPDATE is 'アカウント情報更新フラグ';
comment on column USER_ALIAS.ROW_ID is '行ID';
comment on column USER_ALIAS.INSERT_USER is '登録ユーザ';
comment on column USER_ALIAS.INSERT_DATETIME is '登録日時';
comment on column USER_ALIAS.UPDATE_USER is '更新ユーザ';
comment on column USER_ALIAS.UPDATE_DATETIME is '更新日時';
comment on column USER_ALIAS.DELETE_FLAG is '削除フラグ';

-- LDAP_CONFIGS へ DESCRIPTION を追加
ALTER TABLE LDAP_CONFIGS DROP COLUMN IF EXISTS DESCRIPTION;
ALTER TABLE LDAP_CONFIGS ADD COLUMN DESCRIPTION character varying(64);
comment on column LDAP_CONFIGS.SYSTEM_NAME is '設定名';
comment on column LDAP_CONFIGS.DESCRIPTION is 'DESCRIPTION';




