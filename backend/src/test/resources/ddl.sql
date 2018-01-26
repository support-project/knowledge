-- BLOBのテストテーブル
drop table if exists BLOB_TABLE cascade;

create table BLOB_TABLE (
  NO bigint not null AUTO_INCREMENT
  , BLOB BLOB
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint BLOB_TABLE_PKC primary key (NO)
) ;

-- CLOBのテストテーブル
drop table if exists CLOB_TABLE cascade;

create table CLOB_TABLE (
  NO integer not null AUTO_INCREMENT
  , CONTENTS text
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint CLOB_TABLE_PKC primary key (NO)
) ;

-- 採番のテストテーブル
drop table if exists AUTO_NO cascade;

create table AUTO_NO (
  no bigint not null AUTO_INCREMENT
  , str VARCHAR(128)
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint AUTO_NO_PKC primary key (no)
) ;

-- 組織以外の役割の紐付
drop table if exists USER_ROLE_RELATIONSHIP cascade;

create table USER_ROLE_RELATIONSHIP (
  EMPLOYEE_ID VARCHAR(15) not null
  , ROLE_ID VARCHAR(20) not null
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint USER_ROLE_RELATIONSHIP_PKC primary key (EMPLOYEE_ID,ROLE_ID)
) ;

-- 役割
drop table if exists ROLE cascade;

create table ROLE (
  ROLE_ID VARCHAR(20) not null
  , ROLE_NAME VARCHAR(50) not null
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint ROLE_PKC primary key (ROLE_ID)
) ;

-- 所属
drop table if exists GROUP_USER_RELATIONSHIP cascade;

create table GROUP_USER_RELATIONSHIP (
  SECTION_CODE VARCHAR(10) not null
  , EMPLOYEE_ID VARCHAR(15) not null
  , ROLE_ID VARCHAR(20) not null
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint GROUP_USER_RELATIONSHIP_PKC primary key (SECTION_CODE,EMPLOYEE_ID)
) ;

-- グループの親子関係
drop table if exists GROUP_RELATIONSHIP cascade;

create table GROUP_RELATIONSHIP (
  PARENT_SECTION_CODE VARCHAR(10) not null
  , CHILD_SECTION_CODE VARCHAR(10) not null
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint GROUP_RELATIONSHIP_PKC primary key (PARENT_SECTION_CODE,CHILD_SECTION_CODE)
) ;

-- 組織
drop table if exists SECTION cascade;

create table SECTION (
  SECTION_CODE VARCHAR(10) not null
  , SECTION_NAME VARCHAR(100)
  , SECTION_SYNONYM VARCHAR(20)
  , CAMPANY_CODE VARCHAR(10) not null
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint SECTION_PKC primary key (SECTION_CODE)
) ;

-- 従業員
drop table if exists EMPLOYEE cascade;

create table EMPLOYEE (
  EMPLOYEE_ID VARCHAR(15) not null
  , EMPLOYEE_NAME VARCHAR(50)
  , PASSWORD VARCHAR(124)
  , MAIL_ADRESS VARCHAR(256)
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint EMPLOYEE_PKC primary key (EMPLOYEE_ID)
) ;

-- 会社
drop table if exists CAMPANY cascade;

create table CAMPANY (
  CAMPANY_CODE VARCHAR(10) not null
  , CAMPANY_NAME VARCHAR(100) not null
  , ADRESS VARCHAR(500)
  , INSERT_USER VARCHAR(15)
  , INSERT_DATETIME timestamp
  , UPDATE_USER VARCHAR(15)
  , UPDATE_DATETIME timestamp
  , constraint CAMPANY_PKC primary key (CAMPANY_CODE)
) ;

alter table USER_ROLE_RELATIONSHIP
  add constraint USER_ROLE_RELATIONSHIP_FK1 foreign key (ROLE_ID) references ROLE(ROLE_ID)
  on delete cascade
  on update cascade;

alter table USER_ROLE_RELATIONSHIP
  add constraint USER_ROLE_RELATIONSHIP_FK2 foreign key (EMPLOYEE_ID) references EMPLOYEE(EMPLOYEE_ID)
  on delete cascade
  on update cascade;

alter table GROUP_USER_RELATIONSHIP
  add constraint GROUP_USER_RELATIONSHIP_FK1 foreign key (ROLE_ID) references ROLE(ROLE_ID)
  on delete cascade
  on update cascade;

alter table GROUP_USER_RELATIONSHIP
  add constraint GROUP_USER_RELATIONSHIP_FK2 foreign key (EMPLOYEE_ID) references EMPLOYEE(EMPLOYEE_ID)
  on delete cascade
  on update cascade;

alter table GROUP_USER_RELATIONSHIP
  add constraint GROUP_USER_RELATIONSHIP_FK3 foreign key (SECTION_CODE) references SECTION(SECTION_CODE)
  on delete cascade
  on update cascade;

alter table GROUP_RELATIONSHIP
  add constraint GROUP_RELATIONSHIP_FK1 foreign key (CHILD_SECTION_CODE) references SECTION(SECTION_CODE)
  on delete cascade
  on update cascade;

alter table GROUP_RELATIONSHIP
  add constraint GROUP_RELATIONSHIP_FK2 foreign key (PARENT_SECTION_CODE) references SECTION(SECTION_CODE)
  on delete cascade
  on update cascade;

alter table SECTION
  add constraint SECTION_FK1 foreign key (CAMPANY_CODE) references CAMPANY(CAMPANY_CODE)
  on delete cascade
  on update cascade;

comment on table "BLOB_TABLE" is 'BLOBのテストテーブル';
comment on column "BLOB_TABLE".NO is '番号';
comment on column "BLOB_TABLE".BLOB is 'BLOB';
comment on column "BLOB_TABLE".INSERT_USER is '登録ユーザ';
comment on column "BLOB_TABLE".INSERT_DATETIME is '登録日時';
comment on column "BLOB_TABLE".UPDATE_USER is '更新ユーザ';
comment on column "BLOB_TABLE".UPDATE_DATETIME is '更新日時';

comment on table CLOB_TABLE is 'CLOBのテストテーブル';
comment on column CLOB_TABLE.NO is '番号';
comment on column CLOB_TABLE.CONTENTS is '内容';
comment on column CLOB_TABLE.INSERT_USER is '登録ユーザ';
comment on column CLOB_TABLE.INSERT_DATETIME is '登録日時';
comment on column CLOB_TABLE.UPDATE_USER is '更新ユーザ';
comment on column CLOB_TABLE.UPDATE_DATETIME is '更新日時';

comment on table AUTO_NO is '採番のテストテーブル';
comment on column AUTO_NO.no is '番号';
comment on column AUTO_NO.str is '文字列';
comment on column AUTO_NO.INSERT_USER is '登録ユーザ';
comment on column AUTO_NO.INSERT_DATETIME is '登録日時';
comment on column AUTO_NO.UPDATE_USER is '更新ユーザ';
comment on column AUTO_NO.UPDATE_DATETIME is '更新日時';

comment on table USER_ROLE_RELATIONSHIP is '組織以外の役割の紐付';
comment on column USER_ROLE_RELATIONSHIP.EMPLOYEE_ID is '従業員ID';
comment on column USER_ROLE_RELATIONSHIP.ROLE_ID is '役割ID';
comment on column USER_ROLE_RELATIONSHIP.INSERT_USER is '登録ユーザ';
comment on column USER_ROLE_RELATIONSHIP.INSERT_DATETIME is '登録日時';
comment on column USER_ROLE_RELATIONSHIP.UPDATE_USER is '更新ユーザ';
comment on column USER_ROLE_RELATIONSHIP.UPDATE_DATETIME is '更新日時';

comment on table ROLE is '役割';
comment on column ROLE.ROLE_ID is '役割ID';
comment on column ROLE.ROLE_NAME is '役割名';
comment on column ROLE.INSERT_USER is '登録ユーザ';
comment on column ROLE.INSERT_DATETIME is '登録日時';
comment on column ROLE.UPDATE_USER is '更新ユーザ';
comment on column ROLE.UPDATE_DATETIME is '更新日時';

comment on table GROUP_USER_RELATIONSHIP is '所属';
comment on column GROUP_USER_RELATIONSHIP.SECTION_CODE is '組織コード';
comment on column GROUP_USER_RELATIONSHIP.EMPLOYEE_ID is '従業員ID';
comment on column GROUP_USER_RELATIONSHIP.ROLE_ID is '役割ID(組織の役職/POST)';
comment on column GROUP_USER_RELATIONSHIP.INSERT_USER is '登録ユーザ';
comment on column GROUP_USER_RELATIONSHIP.INSERT_DATETIME is '登録日時';
comment on column GROUP_USER_RELATIONSHIP.UPDATE_USER is '更新ユーザ';
comment on column GROUP_USER_RELATIONSHIP.UPDATE_DATETIME is '更新日時';

comment on table GROUP_RELATIONSHIP is 'グループの親子関係';
comment on column GROUP_RELATIONSHIP.PARENT_SECTION_CODE is '親組織コード';
comment on column GROUP_RELATIONSHIP.CHILD_SECTION_CODE is '子組織コード';
comment on column GROUP_RELATIONSHIP.INSERT_USER is '登録ユーザ';
comment on column GROUP_RELATIONSHIP.INSERT_DATETIME is '登録日時';
comment on column GROUP_RELATIONSHIP.UPDATE_USER is '更新ユーザ';
comment on column GROUP_RELATIONSHIP.UPDATE_DATETIME is '更新日時';

comment on table SECTION is '組織';
comment on column SECTION.SECTION_CODE is '組織コード';
comment on column SECTION.SECTION_NAME is '組織名';
comment on column SECTION.SECTION_SYNONYM is '組織略称';
comment on column SECTION.CAMPANY_CODE is '会社コード';
comment on column SECTION.INSERT_USER is '登録ユーザ';
comment on column SECTION.INSERT_DATETIME is '登録日時';
comment on column SECTION.UPDATE_USER is '更新ユーザ';
comment on column SECTION.UPDATE_DATETIME is '更新日時';

comment on table EMPLOYEE is '従業員';
comment on column EMPLOYEE.EMPLOYEE_ID is '従業員ID';
comment on column EMPLOYEE.EMPLOYEE_NAME is '従業員名';
comment on column EMPLOYEE.PASSWORD is 'パスワード';
comment on column EMPLOYEE.MAIL_ADRESS is 'メールアドレス';
comment on column EMPLOYEE.INSERT_USER is '登録ユーザ';
comment on column EMPLOYEE.INSERT_DATETIME is '登録日時';
comment on column EMPLOYEE.UPDATE_USER is '更新ユーザ';
comment on column EMPLOYEE.UPDATE_DATETIME is '更新日時';

comment on table CAMPANY is '会社';
comment on column CAMPANY.CAMPANY_CODE is '会社コード';
comment on column CAMPANY.CAMPANY_NAME is '会社名';
comment on column CAMPANY.ADRESS is '所在地';
comment on column CAMPANY.INSERT_USER is '登録ユーザ';
comment on column CAMPANY.INSERT_DATETIME is '登録日時';
comment on column CAMPANY.UPDATE_USER is '更新ユーザ';
comment on column CAMPANY.UPDATE_DATETIME is '更新日時';
