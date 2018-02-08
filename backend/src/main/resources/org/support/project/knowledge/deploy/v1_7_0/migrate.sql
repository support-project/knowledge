ALTER TABLE KNOWLEDGES DROP COLUMN IF EXISTS NOTIFY_STATUS;
ALTER TABLE KNOWLEDGES ADD COLUMN NOTIFY_STATUS integer;
comment on column KNOWLEDGES.NOTIFY_STATUS is '通知ステータス';


ALTER TABLE KNOWLEDGE_FILES DROP COLUMN IF EXISTS DRAFT_ID;
ALTER TABLE KNOWLEDGE_FILES ADD COLUMN DRAFT_ID bigint;
comment on column KNOWLEDGE_FILES.DRAFT_ID is '下書きID';


-- ナレッジの項目値
drop table if exists DRAFT_ITEM_VALUES cascade;

create table DRAFT_ITEM_VALUES (
  DRAFT_ID bigint not null
  , TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , ITEM_STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint DRAFT_ITEM_VALUES_PKC primary key (DRAFT_ID,TYPE_ID,ITEM_NO)
) ;

-- ナレッジの下書き
drop table if exists DRAFT_KNOWLEDGES cascade;

create table DRAFT_KNOWLEDGES (
  DRAFT_ID BIGSERIAL not null
  , KNOWLEDGE_ID bigint
  , TITLE character varying(1024) not null
  , CONTENT text
  , PUBLIC_FLAG integer
  , ACCESSES text
  , EDITORS text
  , TAG_NAMES text
  , TYPE_ID integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint DRAFT_KNOWLEDGES_PKC primary key (DRAFT_ID)
) ;

comment on table DRAFT_ITEM_VALUES is 'ナレッジの項目値';
comment on column DRAFT_ITEM_VALUES.DRAFT_ID is '下書きID';
comment on column DRAFT_ITEM_VALUES.TYPE_ID is 'テンプレートの種類ID';
comment on column DRAFT_ITEM_VALUES.ITEM_NO is '項目NO';
comment on column DRAFT_ITEM_VALUES.ITEM_VALUE is '項目値';
comment on column DRAFT_ITEM_VALUES.ITEM_STATUS is 'ステータス';
comment on column DRAFT_ITEM_VALUES.INSERT_USER is '登録ユーザ';
comment on column DRAFT_ITEM_VALUES.INSERT_DATETIME is '登録日時';
comment on column DRAFT_ITEM_VALUES.UPDATE_USER is '更新ユーザ';
comment on column DRAFT_ITEM_VALUES.UPDATE_DATETIME is '更新日時';
comment on column DRAFT_ITEM_VALUES.DELETE_FLAG is '削除フラグ';

comment on table DRAFT_KNOWLEDGES is 'ナレッジの下書き';
comment on column DRAFT_KNOWLEDGES.DRAFT_ID is '下書きID';
comment on column DRAFT_KNOWLEDGES.KNOWLEDGE_ID is 'ナレッジID';
comment on column DRAFT_KNOWLEDGES.TITLE is 'タイトル';
comment on column DRAFT_KNOWLEDGES.CONTENT is '内容';
comment on column DRAFT_KNOWLEDGES.PUBLIC_FLAG is '公開区分';
comment on column DRAFT_KNOWLEDGES.ACCESSES is '公開対象';
comment on column DRAFT_KNOWLEDGES.EDITORS is '共同編集対象';
comment on column DRAFT_KNOWLEDGES.TAG_NAMES is 'タグ名称一覧';
comment on column DRAFT_KNOWLEDGES.TYPE_ID is 'テンプレートの種類ID';
comment on column DRAFT_KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column DRAFT_KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column DRAFT_KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column DRAFT_KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column DRAFT_KNOWLEDGES.DELETE_FLAG is '削除フラグ';







