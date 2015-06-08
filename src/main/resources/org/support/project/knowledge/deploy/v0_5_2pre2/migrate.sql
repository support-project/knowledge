-- 編集可能なグループ
drop table if exists KNOWLEDGE_EDIT_GROUPS cascade;

create table KNOWLEDGE_EDIT_GROUPS (
  KNOWLEDGE_ID bigint not null
  , GROUP_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_EDIT_GROUPS_PKC primary key (KNOWLEDGE_ID,GROUP_ID)
) ;

-- 編集可能なユーザ
drop table if exists KNOWLEDGE_EDIT_USERS cascade;

create table KNOWLEDGE_EDIT_USERS (
  KNOWLEDGE_ID bigint not null
  , USER_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_EDIT_USERS_PKC primary key (KNOWLEDGE_ID,USER_ID)
) ;


comment on table KNOWLEDGE_EDIT_GROUPS is '編集可能なグループ';
comment on column KNOWLEDGE_EDIT_GROUPS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_EDIT_GROUPS.GROUP_ID is 'GROUP_ID';
comment on column KNOWLEDGE_EDIT_GROUPS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_EDIT_GROUPS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_EDIT_GROUPS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_EDIT_GROUPS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_EDIT_GROUPS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_EDIT_USERS is '編集可能なユーザ';
comment on column KNOWLEDGE_EDIT_USERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_EDIT_USERS.USER_ID is 'USER_ID';
comment on column KNOWLEDGE_EDIT_USERS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_EDIT_USERS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_EDIT_USERS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_EDIT_USERS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_EDIT_USERS.DELETE_FLAG is '削除フラグ';

