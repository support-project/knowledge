-- ナレッジ更新履歴
drop table if exists KNOWLEDGE_HISTORIES cascade;

create table KNOWLEDGE_HISTORIES (
  KNOWLEDGE_ID bigint not null
  , HISTORY_NO integer not null
  , TITLE character varying(1024) not null
  , CONTENT text
  , PUBLIC_FLAG integer
  , TAG_IDS character varying(1024)
  , TAG_NAMES text
  , LIKE_COUNT bigint
  , COMMENT_COUNT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_HISTORIES_PKC primary key (KNOWLEDGE_ID,HISTORY_NO)
) ;



comment on table KNOWLEDGE_HISTORIES is 'ナレッジ更新履歴';
comment on column KNOWLEDGE_HISTORIES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_HISTORIES.HISTORY_NO is '履歴番号';
comment on column KNOWLEDGE_HISTORIES.TITLE is 'タイトル';
comment on column KNOWLEDGE_HISTORIES.CONTENT is '内容';
comment on column KNOWLEDGE_HISTORIES.PUBLIC_FLAG is '公開区分';
comment on column KNOWLEDGE_HISTORIES.TAG_IDS is 'タグID一覧';
comment on column KNOWLEDGE_HISTORIES.TAG_NAMES is 'タグ名称一覧';
comment on column KNOWLEDGE_HISTORIES.LIKE_COUNT is 'いいね件数';
comment on column KNOWLEDGE_HISTORIES.COMMENT_COUNT is 'コメント件数';
comment on column KNOWLEDGE_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_HISTORIES.DELETE_FLAG is '削除フラグ';

