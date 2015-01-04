-- アカウントの画像
drop table if exists ACCOUNT_IMAGES cascade;

create table ACCOUNT_IMAGES (
  IMAGE_ID bigint not null AUTO_INCREMENT
  , USER_ID integer
  , FILE_NAME character varying(256)
  , FILE_SIZE double precision
  , FILE_BINARY blob
  , EXTENSION character varying(256)
  , CONTENT_TYPE character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ACCOUNT_IMAGES_PKC primary key (IMAGE_ID)
) ;

create unique index IDX_ACCOUNT_IMAGES_USER_ID
  on ACCOUNT_IMAGES(USER_ID);

create index IDX_LIKES_KNOWLEDGE_ID
  on LIKES(KNOWLEDGE_ID);

create index IDX_COMMENTS_KNOWLEDGE_ID
  on COMMENTS(KNOWLEDGE_ID);

create index IDX_VOTES_KNOWLEDGE_ID
  on VOTES(KNOWLEDGE_ID);

create index IDX_VIEW_HISTORIES_KNOWLEDGE_ID
  on VIEW_HISTORIES(KNOWLEDGE_ID);

create index IDX_KNOWLEDGE_FILES_KNOWLEDGE_ID
  on KNOWLEDGE_FILES(KNOWLEDGE_ID);

ALTER TABLE GROUPS ADD GROUP_CLASS integer;

comment on table ACCOUNT_IMAGES is 'アカウントの画像';
comment on column ACCOUNT_IMAGES.IMAGE_ID is 'IMAGE_ID';
comment on column ACCOUNT_IMAGES.USER_ID is 'ユーザID';
comment on column ACCOUNT_IMAGES.FILE_NAME is 'ファイル名';
comment on column ACCOUNT_IMAGES.FILE_SIZE is 'ファイルサイズ';
comment on column ACCOUNT_IMAGES.FILE_BINARY is 'バイナリ';
comment on column ACCOUNT_IMAGES.EXTENSION is '拡張子';
comment on column ACCOUNT_IMAGES.CONTENT_TYPE is 'CONTENT_TYPE';
comment on column ACCOUNT_IMAGES.INSERT_USER is '登録ユーザ';
comment on column ACCOUNT_IMAGES.INSERT_DATETIME is '登録日時';
comment on column ACCOUNT_IMAGES.UPDATE_USER is '更新ユーザ';
comment on column ACCOUNT_IMAGES.UPDATE_DATETIME is '更新日時';
comment on column ACCOUNT_IMAGES.DELETE_FLAG is '削除フラグ';

comment on column GROUPS.GROUP_CLASS is 'グループの区分';


