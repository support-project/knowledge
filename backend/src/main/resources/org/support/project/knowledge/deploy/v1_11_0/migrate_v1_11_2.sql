-- ユーザのポイント獲得履歴
drop table if exists POINT_USER_HISTORIES cascade;

create table POINT_USER_HISTORIES (
  USER_ID INTEGER not null
  , HISTORY_NO BIGINT not null
  , ACTIVITY_NO BIGINT not null
  , TYPE integer not null
  , POINT integer not null
  , BEFORE_TOTAL integer not null
  , TOTAL integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint POINT_USER_HISTORIES_PKC primary key (USER_ID,HISTORY_NO)
) ;

create index IDX_POINT_USER_HISTORIES_INSERT_DATETIME
  on POINT_USER_HISTORIES(INSERT_DATETIME);

-- ナレッジのポイント獲得履歴
drop table if exists POINT_KNOWLEDGE_HISTORIES cascade;

create table POINT_KNOWLEDGE_HISTORIES (
  KNOWLEDGE_ID BIGINT not null
  , HISTORY_NO BIGINT not null
  , ACTIVITY_NO BIGINT not null
  , TYPE integer not null
  , POINT integer not null
  , BEFORE_TOTAL integer not null
  , TOTAL integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint POINT_KNOWLEDGE_HISTORIES_PKC primary key (KNOWLEDGE_ID,HISTORY_NO)
) ;

create index IDX_POINT_KNOWLEDGE_HISTORIES_INSERT_DATETIME
  on POINT_KNOWLEDGE_HISTORIES(INSERT_DATETIME);

-- ユーザの称号
drop table if exists USER_BADGES cascade;

create table USER_BADGES (
  USER_ID INTEGER not null
  , NO INTEGER not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_BADGES_PKC primary key (USER_ID,NO)
) ;

-- アクティビティ
drop table if exists ACTIVITIES cascade;

create table ACTIVITIES (
  ACTIVITY_NO BIGSERIAL not null
  , USER_ID INTEGER not null
  , KIND integer not null
  , TARGET character varying(64) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ACTIVITIES_PKC primary key (ACTIVITY_NO)
) ;

create index IDX_ACTIVITIES_USER_ID
  on ACTIVITIES(USER_ID);

create index IDX_ACTIVITIES_KIND_TARGET
  on ACTIVITIES(KIND,TARGET);

-- 称号
drop table if exists BADGES cascade;

create table BADGES (
  NO SERIAL not null
  , NAME character varying(128) not null
  , DISPLAY_TEXT character varying(32) not null
  , DESCRIPTION text
  , IMAGE character varying(64)
  , POINT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint BADGES_PKC primary key (NO)
) ;


comment on table POINT_USER_HISTORIES is 'ユーザのポイント獲得履歴';
comment on column POINT_USER_HISTORIES.USER_ID is 'ユーザID';
comment on column POINT_USER_HISTORIES.HISTORY_NO is '履歴番号';
comment on column POINT_USER_HISTORIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column POINT_USER_HISTORIES.TYPE is '獲得のタイプ';
comment on column POINT_USER_HISTORIES.POINT is '獲得ポイント';
comment on column POINT_USER_HISTORIES.BEFORE_TOTAL is '獲得前ポイント';
comment on column POINT_USER_HISTORIES.TOTAL is 'トータルポイント';
comment on column POINT_USER_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column POINT_USER_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column POINT_USER_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column POINT_USER_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column POINT_USER_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table POINT_KNOWLEDGE_HISTORIES is 'ナレッジのポイント獲得履歴';
comment on column POINT_KNOWLEDGE_HISTORIES.KNOWLEDGE_ID is 'ナレッジID';
comment on column POINT_KNOWLEDGE_HISTORIES.HISTORY_NO is '履歴番号';
comment on column POINT_KNOWLEDGE_HISTORIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column POINT_KNOWLEDGE_HISTORIES.TYPE is '獲得のタイプ';
comment on column POINT_KNOWLEDGE_HISTORIES.POINT is '獲得ポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.BEFORE_TOTAL is '獲得前ポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.TOTAL is 'トータルポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column POINT_KNOWLEDGE_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column POINT_KNOWLEDGE_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column POINT_KNOWLEDGE_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column POINT_KNOWLEDGE_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table USER_BADGES is 'ユーザの称号';
comment on column USER_BADGES.USER_ID is 'ユーザID';
comment on column USER_BADGES.NO is '番号';
comment on column USER_BADGES.INSERT_USER is '登録ユーザ';
comment on column USER_BADGES.INSERT_DATETIME is '登録日時';
comment on column USER_BADGES.UPDATE_USER is '更新ユーザ';
comment on column USER_BADGES.UPDATE_DATETIME is '更新日時';
comment on column USER_BADGES.DELETE_FLAG is '削除フラグ';

comment on table ACTIVITIES is 'アクティビティ';
comment on column ACTIVITIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column ACTIVITIES.USER_ID is 'イベントをおこしたユーザ';
comment on column ACTIVITIES.KIND is 'アクティビティの種類';
comment on column ACTIVITIES.TARGET is 'ターゲットID';
comment on column ACTIVITIES.INSERT_USER is '登録ユーザ';
comment on column ACTIVITIES.INSERT_DATETIME is '登録日時';
comment on column ACTIVITIES.UPDATE_USER is '更新ユーザ';
comment on column ACTIVITIES.UPDATE_DATETIME is '更新日時';
comment on column ACTIVITIES.DELETE_FLAG is '削除フラグ';

comment on table BADGES is '称号';
comment on column BADGES.NO is '番号';
comment on column BADGES.NAME is '名称';
comment on column BADGES.DISPLAY_TEXT is '表示名';
comment on column BADGES.DESCRIPTION is '説明';
comment on column BADGES.IMAGE is '画像';
comment on column BADGES.POINT is '獲得ポイント';
comment on column BADGES.INSERT_USER is '登録ユーザ';
comment on column BADGES.INSERT_DATETIME is '登録日時';
comment on column BADGES.UPDATE_USER is '更新ユーザ';
comment on column BADGES.UPDATE_DATETIME is '更新日時';
comment on column BADGES.DELETE_FLAG is '削除フラグ';


ALTER TABLE KNOWLEDGES DROP COLUMN IF EXISTS POINT;
ALTER TABLE KNOWLEDGES ADD COLUMN POINT integer default 0 not null;
comment on column KNOWLEDGES.POINT is 'ポイント';

