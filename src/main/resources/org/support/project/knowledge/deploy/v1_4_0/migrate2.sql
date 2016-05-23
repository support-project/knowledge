-- ピン
drop table if exists PINS cascade;

create table PINS (
  NO SERIAL not null
  , KNOWLEDGE_ID bigint not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PINS_PKC primary key (NO)
) ;

create index insert_user on pins (INSERT_USER) ;

comment on table PINS is 'ピン';
comment on column PINS.NO is 'NO';
comment on column PINS.KNOWLEDGE_ID is 'ナレッジID';
comment on column PINS.ROW_ID is '行ID';
comment on column PINS.INSERT_USER is '登録ユーザ';
comment on column PINS.INSERT_DATETIME is '登録日時';
comment on column PINS.UPDATE_USER is '更新ユーザ';
comment on column PINS.UPDATE_DATETIME is '更新日時';
comment on column PINS.DELETE_FLAG is '削除フラグ';