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

-- ユーザへの通知
drop table if exists USER_NOTIFICATIONS cascade;

create table USER_NOTIFICATIONS (
  USER_ID INTEGER not null
  , NO bigint not null
  , STATUS integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_NOTIFICATIONS_PKC primary key (USER_ID,NO)
) ;

-- 通知
drop table if exists NOTIFICATIONS cascade;

create table NOTIFICATIONS (
  NO BIGSERIAL not null
  , TITLE VARCHAR(256)
  , CONTENT text
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTIFICATIONS_PKC primary key (NO)
) ;

-- 既読
drop table if exists READ_MARKS cascade;

create table READ_MARKS (
  NO INTEGER not null
  , USER_ID INTEGER not null
  , SHOW_NEXT_TIME integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint READ_MARKS_PKC primary key (NO,USER_ID)
) ;

-- 告知
drop table if exists NOTICES cascade;

create table NOTICES (
  NO SERIAL not null
  , TITLE character varying(1024)
  , MESSAGE text
  , START_DATETIME timestamp
  , END_DATETIME timestamp
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTICES_PKC primary key (NO)
) ;

-- システム付加情報
drop table if exists SYSTEM_ATTRIBUTES cascade;

create table SYSTEM_ATTRIBUTES (
  SYSTEM_NAME character varying(64) not null
  , CONFIG_NAME character varying(256) not null
  , CONFIG_VALUE text
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SYSTEM_ATTRIBUTES_PKC primary key (SYSTEM_NAME,CONFIG_NAME)
) ;

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

-- プロキシ設定
drop table if exists PROXY_CONFIGS cascade;

create table PROXY_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , PROXY_HOST_NAME character varying(256) not null
  , PROXY_PORT_NO integer not null
  , PROXY_AUTH_TYPE integer not null
  , PROXY_AUTH_USER_ID character varying(256)
  , PROXY_AUTH_PASSWORD character varying(1024)
  , PROXY_AUTH_SALT character varying(1024)
  , PROXY_AUTH_PC_NAME character varying(256)
  , PROXY_AUTH_DOMAIN character varying(256)
  , THIRD_PARTY_CERTIFICATE integer
  , TEST_URL character varying(256)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PROXY_CONFIGS_PKC primary key (SYSTEM_NAME)
) ;

-- LDAP認証設定
drop table if exists LDAP_CONFIGS cascade;

create table LDAP_CONFIGS (
  SYSTEM_NAME character varying(64) not null
  , DESCRIPTION character varying(64)
  , HOST character varying(256) not null
  , PORT integer not null
  , USE_SSL integer
  , USE_TLS integer
  , BIND_DN character varying(256)
  , BIND_PASSWORD character varying(1024)
  , SALT character varying(1024)
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

-- メールアドレス変更確認
drop table if exists CONFIRM_MAIL_CHANGES cascade;

create table CONFIRM_MAIL_CHANGES (
  ID character varying(256) not null
  , USER_ID integer not null
  , MAIL_ADDRESS character varying(256) not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint CONFIRM_MAIL_CHANGES_PKC primary key (ID)
) ;

-- ロケール
drop table if exists LOCALES cascade;

create table LOCALES (
  KEY character varying(12) not null
  , LANGUAGE character varying(4) not null
  , COUNTRY character varying(4)
  , VARIANT character varying(4)
  , DISP_NAME character varying(128)
  , FLAG_ICON character varying(24)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LOCALES_PKC primary key (KEY)
) ;

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
  , LOCALE_KEY character varying(12)
  , MAIL_ADDRESS character varying(256)
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
  , SMTP_PASSWORD character varying(1024)
  , SALT character varying(1024)
  , FROM_ADDRESS character varying(256)
  , FROM_NAME character varying(256)
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

-- ACCESS_LOGS
drop table if exists ACCESS_LOGS cascade;

create table ACCESS_LOGS (
  NO BIGSERIAL not null
  , PATH character varying(1024)
  , IP_ADDRESS character varying(64)
  , USER_AGENT character varying(1024)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ACCESS_LOGS_PKC primary key (NO)
) ;

-- システムの設定
drop table if exists SYSTEMS cascade;

create table SYSTEMS (
  SYSTEM_NAME character varying(64) not null
  , VERSION character varying(16) not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SYSTEMS_PKC primary key (SYSTEM_NAME)
) ;

-- ユーザが所属するグループ
drop table if exists USER_GROUPS cascade;

create table USER_GROUPS (
  USER_ID integer not null
  , GROUP_ID integer not null
  , GROUP_ROLE integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_GROUPS_PKC primary key (USER_ID,GROUP_ID)
) ;

-- グループ
drop table if exists GROUPS cascade;

create table GROUPS (
  GROUP_ID SERIAL not null
  , GROUP_KEY character varying(68) not null
  , GROUP_NAME character varying(128) not null
  , DESCRIPTION character varying(256)
  , PARENT_GROUP_KEY character varying(128)
  , GROUP_CLASS integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint GROUPS_PKC primary key (GROUP_ID)
) ;

-- ユーザ
drop table if exists USERS cascade;

create table USERS (
  USER_ID SERIAL not null
  , USER_KEY character varying(256) not null
  , USER_NAME character varying(256) not null
  , PASSWORD character varying(1024) not null
  , SALT character varying(1024)
  , LOCALE_KEY character varying(12)
  , MAIL_ADDRESS character varying(256)
  , AUTH_LDAP integer
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USERS_PKC primary key (USER_ID)
) ;

create unique index IDX_USERS_USER_KEY
  on USERS(USER_KEY);

-- 権限
drop table if exists ROLES cascade;

create table ROLES (
  ROLE_ID SERIAL not null
  , ROLE_KEY character varying(12) not null
  , ROLE_NAME character varying(50)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ROLES_PKC primary key (ROLE_ID)
) ;

-- ユーザの権限
drop table if exists USER_ROLES cascade;

create table USER_ROLES (
  USER_ID integer not null
  , ROLE_ID integer not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_ROLES_PKC primary key (USER_ID,ROLE_ID)
) ;

-- ログイン履歴
drop table if exists LOGIN_HISTORIES cascade;

create table LOGIN_HISTORIES (
  USER_ID integer not null
  , LOGIN_COUNT double precision not null
  , LODIN_DATE_TIME timestamp not null
  , IP_ADDRESS character varying(15)
  , USER_AGENT character varying(256)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LOGIN_HISTORIES_PKC primary key (USER_ID,LOGIN_COUNT)
) ;

-- 機能
drop table if exists FUNCTIONS cascade;

create table FUNCTIONS (
  FUNCTION_KEY character varying(64) not null
  , DESCRIPTION character varying(256)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint FUNCTIONS_PKC primary key (FUNCTION_KEY)
) ;

-- 機能にアクセスできる権限
drop table if exists ROLE_FUNCTIONS cascade;

create table ROLE_FUNCTIONS (
  ROLE_ID integer not null
  , FUNCTION_KEY character varying(64) not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ROLE_FUNCTIONS_PKC primary key (ROLE_ID,FUNCTION_KEY)
) ;

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

comment on table USER_NOTIFICATIONS is 'ユーザへの通知';
comment on column USER_NOTIFICATIONS.USER_ID is 'ユーザID';
comment on column USER_NOTIFICATIONS.NO is 'NO';
comment on column USER_NOTIFICATIONS.STATUS is 'ステータス';
comment on column USER_NOTIFICATIONS.ROW_ID is '行ID';
comment on column USER_NOTIFICATIONS.INSERT_USER is '登録ユーザ';
comment on column USER_NOTIFICATIONS.INSERT_DATETIME is '登録日時';
comment on column USER_NOTIFICATIONS.UPDATE_USER is '更新ユーザ';
comment on column USER_NOTIFICATIONS.UPDATE_DATETIME is '更新日時';
comment on column USER_NOTIFICATIONS.DELETE_FLAG is '削除フラグ';

comment on table NOTIFICATIONS is '通知';
comment on column NOTIFICATIONS.NO is 'NO';
comment on column NOTIFICATIONS.TITLE is 'タイトル';
comment on column NOTIFICATIONS.CONTENT is 'メッセージ';
comment on column NOTIFICATIONS.ROW_ID is '行ID';
comment on column NOTIFICATIONS.INSERT_USER is '登録ユーザ';
comment on column NOTIFICATIONS.INSERT_DATETIME is '登録日時';
comment on column NOTIFICATIONS.UPDATE_USER is '更新ユーザ';
comment on column NOTIFICATIONS.UPDATE_DATETIME is '更新日時';
comment on column NOTIFICATIONS.DELETE_FLAG is '削除フラグ';

comment on table READ_MARKS is '既読';
comment on column READ_MARKS.NO is 'NO';
comment on column READ_MARKS.USER_ID is 'ユーザID';
comment on column READ_MARKS.SHOW_NEXT_TIME is '次回も表示する';
comment on column READ_MARKS.ROW_ID is '行ID';
comment on column READ_MARKS.INSERT_USER is '登録ユーザ';
comment on column READ_MARKS.INSERT_DATETIME is '登録日時';
comment on column READ_MARKS.UPDATE_USER is '更新ユーザ';
comment on column READ_MARKS.UPDATE_DATETIME is '更新日時';
comment on column READ_MARKS.DELETE_FLAG is '削除フラグ';

comment on table NOTICES is '告知';
comment on column NOTICES.NO is 'NO';
comment on column NOTICES.TITLE is 'タイトル';
comment on column NOTICES.MESSAGE is 'メッセージ';
comment on column NOTICES.START_DATETIME is '掲示開始日時（UTC）';
comment on column NOTICES.END_DATETIME is '掲示終了日時（UTC）';
comment on column NOTICES.ROW_ID is '行ID';
comment on column NOTICES.INSERT_USER is '登録ユーザ';
comment on column NOTICES.INSERT_DATETIME is '登録日時';
comment on column NOTICES.UPDATE_USER is '更新ユーザ';
comment on column NOTICES.UPDATE_DATETIME is '更新日時';
comment on column NOTICES.DELETE_FLAG is '削除フラグ';

comment on table SYSTEM_ATTRIBUTES is 'システム付加情報';
comment on column SYSTEM_ATTRIBUTES.SYSTEM_NAME is 'システム名';
comment on column SYSTEM_ATTRIBUTES.CONFIG_NAME is 'コンフィグ名';
comment on column SYSTEM_ATTRIBUTES.CONFIG_VALUE is 'コンフィグ値';
comment on column SYSTEM_ATTRIBUTES.ROW_ID is '行ID';
comment on column SYSTEM_ATTRIBUTES.INSERT_USER is '登録ユーザ';
comment on column SYSTEM_ATTRIBUTES.INSERT_DATETIME is '登録日時';
comment on column SYSTEM_ATTRIBUTES.UPDATE_USER is '更新ユーザ';
comment on column SYSTEM_ATTRIBUTES.UPDATE_DATETIME is '更新日時';
comment on column SYSTEM_ATTRIBUTES.DELETE_FLAG is '削除フラグ';

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

comment on table PROXY_CONFIGS is 'プロキシ設定';
comment on column PROXY_CONFIGS.SYSTEM_NAME is 'システム名';
comment on column PROXY_CONFIGS.PROXY_HOST_NAME is '[Proxy]ホスト名';
comment on column PROXY_CONFIGS.PROXY_PORT_NO is '[Proxy]ポート番号';
comment on column PROXY_CONFIGS.PROXY_AUTH_TYPE is '[Proxy-Auth]認証タイプ';
comment on column PROXY_CONFIGS.PROXY_AUTH_USER_ID is '[Proxy-Auth]認証ユーザID';
comment on column PROXY_CONFIGS.PROXY_AUTH_PASSWORD is '[Proxy-Auth]認証パスワード';
comment on column PROXY_CONFIGS.PROXY_AUTH_SALT is '[Proxy-Auth]認証SALT';
comment on column PROXY_CONFIGS.PROXY_AUTH_PC_NAME is '[Proxy-Auth-NTLM]認証PC名';
comment on column PROXY_CONFIGS.PROXY_AUTH_DOMAIN is '[Auth-NTLM]認証ドメイン';
comment on column PROXY_CONFIGS.THIRD_PARTY_CERTIFICATE is '[Web]SSL証明書チェック';
comment on column PROXY_CONFIGS.TEST_URL is '[Web]接続確認用URL';
comment on column PROXY_CONFIGS.ROW_ID is '行ID';
comment on column PROXY_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column PROXY_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column PROXY_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column PROXY_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column PROXY_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table LDAP_CONFIGS is 'LDAP認証設定';
comment on column LDAP_CONFIGS.SYSTEM_NAME is '設定名';
comment on column LDAP_CONFIGS.DESCRIPTION is 'DESCRIPTION';
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
comment on column LDAP_CONFIGS.AUTH_TYPE is 'AUTH_TYPE:0:DB認証,1:LDAP認証,2:DB認証+LDAP認証(LDAP優先)';
comment on column LDAP_CONFIGS.ROW_ID is '行ID';
comment on column LDAP_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column LDAP_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column LDAP_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column LDAP_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column LDAP_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table CONFIRM_MAIL_CHANGES is 'メールアドレス変更確認';
comment on column CONFIRM_MAIL_CHANGES.ID is 'リセット用ID';
comment on column CONFIRM_MAIL_CHANGES.USER_ID is 'ユーザID';
comment on column CONFIRM_MAIL_CHANGES.MAIL_ADDRESS is 'メールアドレス';
comment on column CONFIRM_MAIL_CHANGES.ROW_ID is '行ID';
comment on column CONFIRM_MAIL_CHANGES.INSERT_USER is '登録ユーザ';
comment on column CONFIRM_MAIL_CHANGES.INSERT_DATETIME is '登録日時';
comment on column CONFIRM_MAIL_CHANGES.UPDATE_USER is '更新ユーザ';
comment on column CONFIRM_MAIL_CHANGES.UPDATE_DATETIME is '更新日時';
comment on column CONFIRM_MAIL_CHANGES.DELETE_FLAG is '削除フラグ';

comment on table LOCALES is 'ロケール';
comment on column LOCALES.KEY is 'キー';
comment on column LOCALES.LANGUAGE is '言語';
comment on column LOCALES.COUNTRY is '国';
comment on column LOCALES.VARIANT is 'バリアント';
comment on column LOCALES.DISP_NAME is '表示名';
comment on column LOCALES.FLAG_ICON is '国旗のアイコン';
comment on column LOCALES.ROW_ID is '行ID';
comment on column LOCALES.INSERT_USER is '登録ユーザ';
comment on column LOCALES.INSERT_DATETIME is '登録日時';
comment on column LOCALES.UPDATE_USER is '更新ユーザ';
comment on column LOCALES.UPDATE_DATETIME is '更新日時';
comment on column LOCALES.DELETE_FLAG is '削除フラグ';

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
comment on column PROVISIONAL_REGISTRATIONS.LOCALE_KEY is 'ロケール';
comment on column PROVISIONAL_REGISTRATIONS.MAIL_ADDRESS is 'メールアドレス';
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
comment on column MAIL_CONFIGS.SMTP_PASSWORD is 'SMTP_PASSWORD:暗号化（可逆）';
comment on column MAIL_CONFIGS.SALT is 'SALT';
comment on column MAIL_CONFIGS.FROM_ADDRESS is '送信元';
comment on column MAIL_CONFIGS.FROM_NAME is '送信元名';
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

comment on table ACCESS_LOGS is 'ACCESS_LOGS';
comment on column ACCESS_LOGS.NO is 'NO';
comment on column ACCESS_LOGS.PATH is 'PATH';
comment on column ACCESS_LOGS.IP_ADDRESS is 'IP_ADDRESS';
comment on column ACCESS_LOGS.USER_AGENT is 'USER_AGENT';
comment on column ACCESS_LOGS.ROW_ID is '行ID';
comment on column ACCESS_LOGS.INSERT_USER is '登録ユーザ';
comment on column ACCESS_LOGS.INSERT_DATETIME is '登録日時';
comment on column ACCESS_LOGS.UPDATE_USER is '更新ユーザ';
comment on column ACCESS_LOGS.UPDATE_DATETIME is '更新日時';
comment on column ACCESS_LOGS.DELETE_FLAG is '削除フラグ';

comment on table SYSTEMS is 'システムの設定';
comment on column SYSTEMS.SYSTEM_NAME is 'システム名';
comment on column SYSTEMS.VERSION is 'バージョン';
comment on column SYSTEMS.ROW_ID is '行ID';
comment on column SYSTEMS.INSERT_USER is '登録ユーザ';
comment on column SYSTEMS.INSERT_DATETIME is '登録日時';
comment on column SYSTEMS.UPDATE_USER is '更新ユーザ';
comment on column SYSTEMS.UPDATE_DATETIME is '更新日時';
comment on column SYSTEMS.DELETE_FLAG is '削除フラグ';

comment on table USER_GROUPS is 'ユーザが所属するグループ';
comment on column USER_GROUPS.USER_ID is 'ユーザID';
comment on column USER_GROUPS.GROUP_ID is 'グループID:CHARACTER SET latin1';
comment on column USER_GROUPS.GROUP_ROLE is 'グループの権限';
comment on column USER_GROUPS.ROW_ID is '行ID';
comment on column USER_GROUPS.INSERT_USER is '登録ユーザ';
comment on column USER_GROUPS.INSERT_DATETIME is '登録日時';
comment on column USER_GROUPS.UPDATE_USER is '更新ユーザ';
comment on column USER_GROUPS.UPDATE_DATETIME is '更新日時';
comment on column USER_GROUPS.DELETE_FLAG is '削除フラグ';

comment on table GROUPS is 'グループ';
comment on column GROUPS.GROUP_ID is 'グループID';
comment on column GROUPS.GROUP_KEY is 'グループKEY';
comment on column GROUPS.GROUP_NAME is 'グループ名称';
comment on column GROUPS.DESCRIPTION is '説明';
comment on column GROUPS.PARENT_GROUP_KEY is '親グループKKEY';
comment on column GROUPS.GROUP_CLASS is 'グループの区分';
comment on column GROUPS.ROW_ID is '行ID';
comment on column GROUPS.INSERT_USER is '登録ユーザ';
comment on column GROUPS.INSERT_DATETIME is '登録日時';
comment on column GROUPS.UPDATE_USER is '更新ユーザ';
comment on column GROUPS.UPDATE_DATETIME is '更新日時';
comment on column GROUPS.DELETE_FLAG is '削除フラグ';

comment on table USERS is 'ユーザ';
comment on column USERS.USER_ID is 'ユーザID';
comment on column USERS.USER_KEY is 'ユーザKEY:ユニーク';
comment on column USERS.USER_NAME is 'ユーザ名';
comment on column USERS.PASSWORD is 'パスワード:ハッシュ(不可逆)';
comment on column USERS.SALT is 'SALT';
comment on column USERS.LOCALE_KEY is 'ロケール';
comment on column USERS.MAIL_ADDRESS is 'メールアドレス';
comment on column USERS.AUTH_LDAP is 'LDAP認証ユーザかどうか';
comment on column USERS.ROW_ID is '行ID';
comment on column USERS.INSERT_USER is '登録ユーザ';
comment on column USERS.INSERT_DATETIME is '登録日時';
comment on column USERS.UPDATE_USER is '更新ユーザ';
comment on column USERS.UPDATE_DATETIME is '更新日時';
comment on column USERS.DELETE_FLAG is '削除フラグ';

comment on table ROLES is '権限';
comment on column ROLES.ROLE_ID is '権限ID';
comment on column ROLES.ROLE_KEY is '権限KEY';
comment on column ROLES.ROLE_NAME is '権限名';
comment on column ROLES.ROW_ID is '行ID';
comment on column ROLES.INSERT_USER is '登録ユーザ';
comment on column ROLES.INSERT_DATETIME is '登録日時';
comment on column ROLES.UPDATE_USER is '更新ユーザ';
comment on column ROLES.UPDATE_DATETIME is '更新日時';
comment on column ROLES.DELETE_FLAG is '削除フラグ';

comment on table USER_ROLES is 'ユーザの権限';
comment on column USER_ROLES.USER_ID is 'ユーザID';
comment on column USER_ROLES.ROLE_ID is '権限ID';
comment on column USER_ROLES.ROW_ID is '行ID';
comment on column USER_ROLES.INSERT_USER is '登録ユーザ';
comment on column USER_ROLES.INSERT_DATETIME is '登録日時';
comment on column USER_ROLES.UPDATE_USER is '更新ユーザ';
comment on column USER_ROLES.UPDATE_DATETIME is '更新日時';
comment on column USER_ROLES.DELETE_FLAG is '削除フラグ';

comment on table LOGIN_HISTORIES is 'ログイン履歴';
comment on column LOGIN_HISTORIES.USER_ID is 'ユーザID';
comment on column LOGIN_HISTORIES.LOGIN_COUNT is 'ログイン番号';
comment on column LOGIN_HISTORIES.LODIN_DATE_TIME is 'ログイン日時';
comment on column LOGIN_HISTORIES.IP_ADDRESS is 'IPアドレス';
comment on column LOGIN_HISTORIES.USER_AGENT is 'エージェント';
comment on column LOGIN_HISTORIES.ROW_ID is '行ID';
comment on column LOGIN_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column LOGIN_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column LOGIN_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column LOGIN_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column LOGIN_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table FUNCTIONS is '機能';
comment on column FUNCTIONS.FUNCTION_KEY is '機能';
comment on column FUNCTIONS.DESCRIPTION is '機能の説明';
comment on column FUNCTIONS.ROW_ID is '行ID';
comment on column FUNCTIONS.INSERT_USER is '登録ユーザ';
comment on column FUNCTIONS.INSERT_DATETIME is '登録日時';
comment on column FUNCTIONS.UPDATE_USER is '更新ユーザ';
comment on column FUNCTIONS.UPDATE_DATETIME is '更新日時';
comment on column FUNCTIONS.DELETE_FLAG is '削除フラグ';

comment on table ROLE_FUNCTIONS is '機能にアクセスできる権限';
comment on column ROLE_FUNCTIONS.ROLE_ID is '権限ID';
comment on column ROLE_FUNCTIONS.FUNCTION_KEY is '機能';
comment on column ROLE_FUNCTIONS.ROW_ID is '行ID';
comment on column ROLE_FUNCTIONS.INSERT_USER is '登録ユーザ';
comment on column ROLE_FUNCTIONS.INSERT_DATETIME is '登録日時';
comment on column ROLE_FUNCTIONS.UPDATE_USER is '更新ユーザ';
comment on column ROLE_FUNCTIONS.UPDATE_DATETIME is '更新日時';
comment on column ROLE_FUNCTIONS.DELETE_FLAG is '削除フラグ';
