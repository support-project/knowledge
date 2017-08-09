-- コメントのイイネ
drop table if exists LIKE_COMMENTS cascade;

create table LIKE_COMMENTS (
  NO BIGSERIAL not null
  , COMMENT_NO bigint not null
  , LIKE_CLASS integer default 1
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LIKE_COMMENTS_PKC primary key (NO)
) ;

create index IDX_LIKE_COMMENTS_COMMENT_NO
  on LIKE_COMMENTS(COMMENT_NO);

comment on table LIKE_COMMENTS is 'コメントのイイネ';
comment on column LIKE_COMMENTS.NO is 'NO';
comment on column LIKE_COMMENTS.COMMENT_NO is 'コメント番号';
comment on column LIKE_COMMENTS.LIKE_CLASS is '種類';
comment on column LIKE_COMMENTS.INSERT_USER is '登録ユーザ';
comment on column LIKE_COMMENTS.INSERT_DATETIME is '登録日時';
comment on column LIKE_COMMENTS.UPDATE_USER is '更新ユーザ';
comment on column LIKE_COMMENTS.UPDATE_DATETIME is '更新日時';
comment on column LIKE_COMMENTS.DELETE_FLAG is '削除フラグ';

ALTER TABLE LIKES DROP COLUMN IF EXISTS LIKE_CLASS;
ALTER TABLE LIKES ADD COLUMN LIKE_CLASS integer default 1;

comment on column LIKES.LIKE_CLASS is '種類';

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

