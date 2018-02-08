DELETE FROM TEMPLATE_MASTERS WHERE TYPE_ID = -101;
DELETE FROM TEMPLATE_ITEMS WHERE TYPE_ID = -101;

INSERT INTO TEMPLATE_MASTERS ( TYPE_ID, TYPE_NAME, TYPE_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,'event', 'fa-calendar', '勉強会などのイベント情報をシェアします',0,'2017-02-16 00:00:00',null,null,0);

INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,0,'Date',20,0,'2017-02-16 00:00:00',null,null,0);
INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,1,'Start',21,0,'2017-02-16 00:00:00',null,null,0);
INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,2,'End',21,0,'2017-02-16 00:00:00',null,null,0);
INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,3,'Timezone',22,0,'2017-02-16 00:00:00',null,null,0);
INSERT INTO TEMPLATE_ITEMS ( TYPE_ID, ITEM_NO, ITEM_NAME, ITEM_TYPE, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG ) 
VALUES 
(-101,4,'The number to be accepted',2,0,'2017-02-16 00:00:00',null,null,0);


-- アンケートの回答の値
drop table if exists SURVEY_ITEM_ANSWERS cascade;

create table SURVEY_ITEM_ANSWERS (
  KNOWLEDGE_ID bigint not null
  , ANSWER_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ITEM_ANSWERS_PKC primary key (KNOWLEDGE_ID,ANSWER_ID,ITEM_NO)
) ;

-- アンケートの回答
drop table if exists SURVEY_ANSWERS cascade;

create table SURVEY_ANSWERS (
  KNOWLEDGE_ID bigint not null
  , ANSWER_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ANSWERS_PKC primary key (KNOWLEDGE_ID,ANSWER_ID)
) ;

-- アンケートの選択肢の値
drop table if exists SURVEY_CHOICES cascade;

create table SURVEY_CHOICES (
  KNOWLEDGE_ID bigint not null
  , ITEM_NO integer not null
  , CHOICE_NO integer not null
  , CHOICE_VALUE character varying(256) not null
  , CHOICE_LABEL character varying(256) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_CHOICES_PKC primary key (KNOWLEDGE_ID,ITEM_NO,CHOICE_NO)
) ;

-- アンケート項目
drop table if exists SURVEY_ITEMS cascade;

create table SURVEY_ITEMS (
  KNOWLEDGE_ID bigint not null
  , ITEM_NO integer not null
  , ITEM_NAME character varying(32) not null
  , ITEM_TYPE integer not null
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ITEMS_PKC primary key (KNOWLEDGE_ID,ITEM_NO)
) ;

-- アンケート
drop table if exists SURVEYS cascade;

create table SURVEYS (
  KNOWLEDGE_ID bigint not null
  , TITLE character varying(256) not null
  , DESCRIPTION text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEYS_PKC primary key (KNOWLEDGE_ID)
) ;

-- イベント
drop table if exists EVENTS cascade;

create table EVENTS (
  KNOWLEDGE_ID bigint not null
  , START_DATE_TIME timestamp not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint EVENTS_PKC primary key (KNOWLEDGE_ID)
) ;

-- 参加者
drop table if exists PARTICIPANTS cascade;

create table PARTICIPANTS (
  KNOWLEDGE_ID bigint not null
  , USER_ID integer not null
  , STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PARTICIPANTS_PKC primary key (KNOWLEDGE_ID,USER_ID)
) ;

comment on table SURVEY_ITEM_ANSWERS is 'アンケートの回答の値';
comment on column SURVEY_ITEM_ANSWERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ITEM_ANSWERS.ANSWER_ID is '回答ID';
comment on column SURVEY_ITEM_ANSWERS.ITEM_NO is '項目NO';
comment on column SURVEY_ITEM_ANSWERS.ITEM_VALUE is '項目値';
comment on column SURVEY_ITEM_ANSWERS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ITEM_ANSWERS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ITEM_ANSWERS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ITEM_ANSWERS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ITEM_ANSWERS.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_ANSWERS is 'アンケートの回答';
comment on column SURVEY_ANSWERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ANSWERS.ANSWER_ID is '回答ID';
comment on column SURVEY_ANSWERS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ANSWERS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ANSWERS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ANSWERS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ANSWERS.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_CHOICES is 'アンケートの選択肢の値';
comment on column SURVEY_CHOICES.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_CHOICES.ITEM_NO is '項目NO';
comment on column SURVEY_CHOICES.CHOICE_NO is '選択肢番号';
comment on column SURVEY_CHOICES.CHOICE_VALUE is '選択肢値';
comment on column SURVEY_CHOICES.CHOICE_LABEL is '選択肢ラベル';
comment on column SURVEY_CHOICES.INSERT_USER is '登録ユーザ';
comment on column SURVEY_CHOICES.INSERT_DATETIME is '登録日時';
comment on column SURVEY_CHOICES.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_CHOICES.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_CHOICES.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_ITEMS is 'アンケート項目';
comment on column SURVEY_ITEMS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ITEMS.ITEM_NO is '項目NO';
comment on column SURVEY_ITEMS.ITEM_NAME is '項目名';
comment on column SURVEY_ITEMS.ITEM_TYPE is '項目の種類';
comment on column SURVEY_ITEMS.DESCRIPTION is '説明';
comment on column SURVEY_ITEMS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ITEMS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ITEMS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ITEMS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ITEMS.DELETE_FLAG is '削除フラグ';

comment on table SURVEYS is 'アンケート';
comment on column SURVEYS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEYS.TITLE is 'タイトル';
comment on column SURVEYS.DESCRIPTION is '説明';
comment on column SURVEYS.INSERT_USER is '登録ユーザ';
comment on column SURVEYS.INSERT_DATETIME is '登録日時';
comment on column SURVEYS.UPDATE_USER is '更新ユーザ';
comment on column SURVEYS.UPDATE_DATETIME is '更新日時';
comment on column SURVEYS.DELETE_FLAG is '削除フラグ';

comment on table EVENTS is 'イベント';
comment on column EVENTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column EVENTS.START_DATE_TIME is '開催日     UTC';
comment on column EVENTS.INSERT_USER is '登録ユーザ';
comment on column EVENTS.INSERT_DATETIME is '登録日時';
comment on column EVENTS.UPDATE_USER is '更新ユーザ';
comment on column EVENTS.UPDATE_DATETIME is '更新日時';
comment on column EVENTS.DELETE_FLAG is '削除フラグ';

comment on table PARTICIPANTS is '参加者';
comment on column PARTICIPANTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column PARTICIPANTS.USER_ID is 'ユーザID';
comment on column PARTICIPANTS.STATUS is 'ステータス';
comment on column PARTICIPANTS.INSERT_USER is '登録ユーザ';
comment on column PARTICIPANTS.INSERT_DATETIME is '登録日時';
comment on column PARTICIPANTS.UPDATE_USER is '更新ユーザ';
comment on column PARTICIPANTS.UPDATE_DATETIME is '更新日時';
comment on column PARTICIPANTS.DELETE_FLAG is '削除フラグ';

