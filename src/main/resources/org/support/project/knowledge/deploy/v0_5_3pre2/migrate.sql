INSERT INTO GROUPS 
( GROUP_ID, GROUP_KEY, GROUP_NAME, DESCRIPTION, PARENT_GROUP_KEY, GROUP_CLASS, ROW_ID,
 INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
 VALUES
 (0,'g-all','ALL USERS','全てのユーザが所属するグループ',null,0,'g-all',0,'2015-07-04 00:00:00',null,null,0);

ALTER TABLE USERS DROP COLUMN IF EXISTS MAIL_ADDRESS;
ALTER TABLE USERS DROP COLUMN IF EXISTS AUTH_LDAP;
ALTER TABLE PROVISIONAL_REGISTRATIONS DROP COLUMN IF EXISTS MAIL_ADDRESS;
ALTER TABLE USERS ADD COLUMN MAIL_ADDRESS character varying(256);
ALTER TABLE USERS ADD COLUMN AUTH_LDAP integer;
ALTER TABLE PROVISIONAL_REGISTRATIONS ADD COLUMN MAIL_ADDRESS character varying(256);

comment on column USERS.MAIL_ADDRESS is 'メールアドレス';
comment on column PROVISIONAL_REGISTRATIONS.MAIL_ADDRESS is 'メールアドレス';
comment on column USERS.AUTH_LDAP is 'LDAP認証ユーザかどうか';

-- LDAP認証設定
drop table if exists LDAP_CONFIGS cascade;

create table LDAP_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , HOST character varying(256) not null
  , PORT integer not null
  , USE_SSL integer
  , USE_TLS integer
  , BIND_DN character varying(256)
  , BIND_PASSWORD character varying(1048)
  , SALT character varying(1048)
  , BASE_DN character varying(256) not null
  , FILTER character varying(256)
  , ID_ATTR character varying(256) not null
  , NAME_ATTR character varying(256)
  , MAIL_ATTR character varying(256)
  , ADMIN_CHECK_FILTER character varying(256)
  , AUTH_TYPE integer not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LDAP_CONFIGS_PKC primary key (SYSTEM_NAME)
) ;

comment on table LDAP_CONFIGS is 'LDAP認証設定';
comment on column LDAP_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column LDAP_CONFIGS.HOST is 'HOST';
comment on column LDAP_CONFIGS.PORT is 'PORT';
comment on column LDAP_CONFIGS.USE_SSL is 'USE_SSL';
comment on column LDAP_CONFIGS.USE_TLS is 'USE_TLS';
comment on column LDAP_CONFIGS.BIND_DN is 'BIND_DN';
comment on column LDAP_CONFIGS.BIND_PASSWORD is 'BIND_PASSWORD';
comment on column LDAP_CONFIGS.SALT is 'SALT';
comment on column LDAP_CONFIGS.BASE_DN is 'BASE_DN';
comment on column LDAP_CONFIGS.FILTER is 'FILTER';
comment on column LDAP_CONFIGS.ID_ATTR is 'ID_ATTR';
comment on column LDAP_CONFIGS.NAME_ATTR is 'NAME_ATTR';
comment on column LDAP_CONFIGS.MAIL_ATTR is 'MAIL_ATTR';
comment on column LDAP_CONFIGS.ADMIN_CHECK_FILTER is 'ADMIN_CHECK_FILTER';
comment on column LDAP_CONFIGS.AUTH_TYPE is 'AUTH_TYPE	 0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先)';
comment on column LDAP_CONFIGS.ROW_ID is '行ID';
comment on column LDAP_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column LDAP_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column LDAP_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column LDAP_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column LDAP_CONFIGS.DELETE_FLAG is '削除フラグ';


