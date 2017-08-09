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

