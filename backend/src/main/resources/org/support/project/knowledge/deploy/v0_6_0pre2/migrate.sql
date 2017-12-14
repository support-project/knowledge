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








-- ストック
drop table if exists STOCKS cascade;

create table STOCKS (
  STOCK_ID BIGSERIAL not null
  , STOCK_NAME character varying(256) not null
  , STOCK_TYPE integer not null
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint STOCKS_PKC primary key (STOCK_ID)
) ;

-- ナレッジの項目値
drop table if exists KNOWLEDGE_ITEM_VALUES cascade;

create table KNOWLEDGE_ITEM_VALUES (
  KNOWLEDGE_ID bigint not null
  , TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , ITEM_STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_ITEM_VALUES_PKC primary key (KNOWLEDGE_ID,TYPE_ID,ITEM_NO)
) ;

-- 選択肢の値
drop table if exists ITEM_CHOICES cascade;

create table ITEM_CHOICES (
  TYPE_ID integer not null
  , ITEM_NO integer not null
  , CHOICE_NO integer not null
  , CHOICE_VALUE character varying(256) not null
  , CHOICE_LABEL character varying(256) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ITEM_CHOICES_PKC primary key (TYPE_ID,ITEM_NO,CHOICE_NO)
) ;

-- テンプレートのマスタ
drop table if exists TEMPLATE_MASTERS cascade;

create table TEMPLATE_MASTERS (
  TYPE_ID serial not null
  , TYPE_NAME character varying(256) not null
  , TYPE_ICON character varying(64)
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TEMPLATE_MASTERS_PKC primary key (TYPE_ID)
) ;

-- テンプレートの項目
drop table if exists TEMPLATE_ITEMS cascade;

create table TEMPLATE_ITEMS (
  TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_NAME character varying(32) not null
  , ITEM_TYPE integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TEMPLATE_ITEMS_PKC primary key (TYPE_ID,ITEM_NO)
) ;


-- ストックしたナレッジ
drop table if exists STOCK_KNOWLEDGES cascade;

create table STOCK_KNOWLEDGES (
  STOCK_ID bigint not null
  , KNOWLEDGE_ID bigint not null
  , COMMENT character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint STOCK_KNOWLEDGES_PKC primary key (STOCK_ID,KNOWLEDGE_ID)
) ;

comment on table STOCKS is 'ストック';
comment on column STOCKS.STOCK_ID is 'STOCK ID';
comment on column STOCKS.STOCK_NAME is 'STOCK 名';
comment on column STOCKS.STOCK_TYPE is '区分';
comment on column STOCKS.DESCRIPTION is '説明';
comment on column STOCKS.INSERT_USER is '登録ユーザ';
comment on column STOCKS.INSERT_DATETIME is '登録日時';
comment on column STOCKS.UPDATE_USER is '更新ユーザ';
comment on column STOCKS.UPDATE_DATETIME is '更新日時';
comment on column STOCKS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_ITEM_VALUES is 'ナレッジの項目値';
comment on column KNOWLEDGE_ITEM_VALUES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_ITEM_VALUES.TYPE_ID is 'テンプレートの種類ID';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_NO is '項目NO';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_VALUE is '項目値';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_STATUS is 'ステータス';
comment on column KNOWLEDGE_ITEM_VALUES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_ITEM_VALUES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_ITEM_VALUES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_ITEM_VALUES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_ITEM_VALUES.DELETE_FLAG is '削除フラグ';

comment on table ITEM_CHOICES is '選択肢の値';
comment on column ITEM_CHOICES.TYPE_ID is 'テンプレートの種類ID';
comment on column ITEM_CHOICES.ITEM_NO is '項目NO';
comment on column ITEM_CHOICES.CHOICE_NO is '選択肢番号';
comment on column ITEM_CHOICES.CHOICE_VALUE is '選択肢値';
comment on column ITEM_CHOICES.CHOICE_LABEL is '選択肢ラベル';
comment on column ITEM_CHOICES.INSERT_USER is '登録ユーザ';
comment on column ITEM_CHOICES.INSERT_DATETIME is '登録日時';
comment on column ITEM_CHOICES.UPDATE_USER is '更新ユーザ';
comment on column ITEM_CHOICES.UPDATE_DATETIME is '更新日時';
comment on column ITEM_CHOICES.DELETE_FLAG is '削除フラグ';

comment on table TEMPLATE_MASTERS is 'テンプレートのマスタ';
comment on column TEMPLATE_MASTERS.TYPE_ID is 'テンプレートの種類ID';
comment on column TEMPLATE_MASTERS.TYPE_NAME is 'テンプレート名';
comment on column TEMPLATE_MASTERS.TYPE_ICON is 'アイコン';
comment on column TEMPLATE_MASTERS.DESCRIPTION is '説明';
comment on column TEMPLATE_MASTERS.INSERT_USER is '登録ユーザ';
comment on column TEMPLATE_MASTERS.INSERT_DATETIME is '登録日時';
comment on column TEMPLATE_MASTERS.UPDATE_USER is '更新ユーザ';
comment on column TEMPLATE_MASTERS.UPDATE_DATETIME is '更新日時';
comment on column TEMPLATE_MASTERS.DELETE_FLAG is '削除フラグ';

comment on table TEMPLATE_ITEMS is 'テンプレートの項目';
comment on column TEMPLATE_ITEMS.TYPE_ID is 'テンプレートの種類ID';
comment on column TEMPLATE_ITEMS.ITEM_NO is '項目NO';
comment on column TEMPLATE_ITEMS.ITEM_NAME is '項目名';
comment on column TEMPLATE_ITEMS.ITEM_TYPE is '項目の種類';
comment on column TEMPLATE_ITEMS.INSERT_USER is '登録ユーザ';
comment on column TEMPLATE_ITEMS.INSERT_DATETIME is '登録日時';
comment on column TEMPLATE_ITEMS.UPDATE_USER is '更新ユーザ';
comment on column TEMPLATE_ITEMS.UPDATE_DATETIME is '更新日時';
comment on column TEMPLATE_ITEMS.DELETE_FLAG is '削除フラグ';


comment on table STOCK_KNOWLEDGES is 'ストックしたナレッジ';
comment on column STOCK_KNOWLEDGES.STOCK_ID is 'STOCK ID';
comment on column STOCK_KNOWLEDGES.KNOWLEDGE_ID is 'ナレッジID';
comment on column STOCK_KNOWLEDGES.COMMENT is 'コメント';
comment on column STOCK_KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column STOCK_KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column STOCK_KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column STOCK_KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column STOCK_KNOWLEDGES.DELETE_FLAG is '削除フラグ';


ALTER TABLE COMMENTS DROP COLUMN IF EXISTS COMMENT_STATUS;
ALTER TABLE KNOWLEDGES DROP COLUMN IF EXISTS TYPE_ID;
ALTER TABLE COMMENTS ADD COLUMN COMMENT_STATUS integer;
ALTER TABLE KNOWLEDGES ADD COLUMN TYPE_ID integer;

comment on column COMMENTS.COMMENT_STATUS is 'ステータス';
comment on column KNOWLEDGES.TYPE_ID is 'テンプレートの種類ID';




DELETE FROM TEMPLATE_MASTERS WHERE TYPE_ID = -100;
DELETE FROM TEMPLATE_ITEMS WHERE TYPE_ID = -100;
DELETE FROM TEMPLATE_MASTERS WHERE TYPE_ID = -99;
DELETE FROM TEMPLATE_ITEMS WHERE TYPE_ID = -99;

INSERT INTO TEMPLATE_MASTERS ( TYPE_ID, TYPE_NAME, TYPE_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-100,'knowledge', 'fa-book', '通常のナレッジ。設定は変更できません。',0,'2015-09-09 00:00:00',null,null,0);

INSERT INTO TEMPLATE_MASTERS ( TYPE_ID, TYPE_NAME, TYPE_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-99,'bookmark', 'fa-bookmark', 'Webにある情報を共有します。登録したURLの内容を検索可能にします。設定は変更できません。（監理者はProxyの設定を行ってください）',0,'2015-09-09 00:00:00',null,null,0);

INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-99,0,'URL',0,0,'2015-09-09 00:00:00',null,null,0);

UPDATE KNOWLEDGES SET TYPE_ID = -100;
