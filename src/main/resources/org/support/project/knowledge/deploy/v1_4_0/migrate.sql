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

